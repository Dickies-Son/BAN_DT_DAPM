package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ban_dt_dapm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_danh_sach_xac_nhan_admin extends RecyclerView.Adapter<adapter_danh_sach_xac_nhan_admin.UserViewHolder>{
    ArrayList<get_set_dat_hang> lstGetSet;
    Context context;
    public adapter_danh_sach_xac_nhan_admin(ArrayList<get_set_dat_hang> lstGetSet, Context context) {
        this.lstGetSet = lstGetSet;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter_danh_sach_xac_nhan_admin.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nap layout
        View userView = inflater.inflate(R.layout.item_danh_sach_xac_nhan_admin,parent,false);
        //
        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_danh_sach_xac_nhan_admin.UserViewHolder holder, int position) {
        get_set_dat_hang item = lstGetSet.get(position);
        //
        holder.tvTen_nguoi_dungC.setText(item.getTen());
        holder.tvSdt_nguoi_dungC.setText(item.getSo_dien_thoai());
        holder.tvSo_luong_dat_hangC.setText(item.getSoLuong());
        holder.tvTong_tien_dat_hangC.setText(item.getGiaTien());
        holder.tvTrang_thai_dat_hangC.setText(item.getMoTa());
        holder.tvDiachi_nguoi_dungC.setText(item.getDia_chi());
        Picasso.get().load(item.getHinhAnh()).into(holder.ivHinh_anh_dat_hangC);
        holder.iv_xoa_san_phamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xóa tài khoản tại vị trí hiện tại trong ArrayList
                                    get_set_dat_hang item = lstGetSet.get(position);
                                    lstGetSet.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, lstGetSet.size());
                                    // Xóa tài khoản tương ứng trong Firebase Realtime Database
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Nguoi_Dung")
                                            .child(item.getSo_dien_thoai())
                                            .child("DaXacNhan")
                                            .child(item.getIdSP());
                                    ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Xóa thành công, cập nhật giao diện và hiển thị thông báo
                                            Toast.makeText(context, "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xử lý khi xóa thất bại
                                            Toast.makeText(context, "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstGetSet.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen_nguoi_dungC,tvSdt_nguoi_dungC,tvSo_luong_dat_hangC,tvTong_tien_dat_hangC,tvTrang_thai_dat_hangC,tvDiachi_nguoi_dungC;
        ImageView ivHinh_anh_dat_hangC,iv_xoa_san_phamC;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen_nguoi_dungC = itemView.findViewById(R.id.tvTen_nguoi_dung_xac_nhan);
            tvSdt_nguoi_dungC = itemView.findViewById(R.id.tvSdt_nguoi_dung_xac_nhan);
            tvSo_luong_dat_hangC = itemView.findViewById(R.id.tvSo_luong_dat_hang_xac_nhan);
            tvTong_tien_dat_hangC = itemView.findViewById(R.id.tvTong_tien_dat_hang_xac_nhan);
            tvTrang_thai_dat_hangC = itemView.findViewById(R.id.tvTrang_thai_dat_hang_xac_nhan);
            tvDiachi_nguoi_dungC = itemView.findViewById(R.id.tvDiachi_nguoi_dung_xac_nhan);
            ivHinh_anh_dat_hangC = itemView.findViewById(R.id.ivHinh_anh_dat_hang);
            iv_xoa_san_phamC = itemView.findViewById(R.id.iv_xoa_san_pham_xac_nhan);
        }
    }
}
