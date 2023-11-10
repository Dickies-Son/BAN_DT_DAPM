package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_danh_sach_san_pham_admin extends RecyclerView.Adapter<adapter_danh_sach_san_pham_admin.UserViewHolder>{
    ArrayList<get_set_san_pham> lstGetSet;
    Context context;

    public adapter_danh_sach_san_pham_admin(ArrayList<get_set_san_pham> lstGetSet, Context context) {
        this.lstGetSet = lstGetSet;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter_danh_sach_san_pham_admin.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nap layout
        View userView = inflater.inflate(R.layout.item_san_pham_admin,parent,false);
        //
        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_danh_sach_san_pham_admin.UserViewHolder holder, int position) {
        get_set_san_pham item = lstGetSet.get(position);
        holder.tvTen_san_pham_adminC.setText(item.getTenSP());
        holder.tvGia_san_pham_adminC.setText(item.getGiaTien());
        Picasso.get().load(item.getHinhAnh()).into(holder.ivHinh_anh_san_pham_adminC);
        holder.iv_xoa_san_pham_adminC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xóa tài khoản tại vị trí hiện tại trong ArrayList
                                    get_set_san_pham item = lstGetSet.get(position);
                                    lstGetSet.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, lstGetSet.size());
                                    // Xóa tài khoản tương ứng trong Firebase Realtime Database
                                    if (item.getLoaiSP().equals("dienthoai")) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Dien_Thoai").child(item.getTenSP());
                                        ref.removeValue();
                                    } else if (item.getLoaiSP().equals("laptop")) {
                                        DatabaseReference ref_1 = FirebaseDatabase.getInstance().getReference("Lap_Top").child(item.getTenSP());
                                        ref_1.removeValue();
                                    } else if (item.getLoaiSP().equals("loa")) {
                                        DatabaseReference ref_2 = FirebaseDatabase.getInstance().getReference("Loa").child(item.getTenSP());
                                        ref_2.removeValue();
                                    }
                                    Toast.makeText(context, "Đã xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
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
    public int getItemCount() {return lstGetSet.size();}

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen_san_pham_adminC,tvGia_san_pham_adminC;
        ImageView ivHinh_anh_san_pham_adminC;
        ImageView iv_xoa_san_pham_adminC;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen_san_pham_adminC = itemView.findViewById(R.id.tvTen_san_pham_admin);
            tvGia_san_pham_adminC = itemView.findViewById(R.id.tvGia_san_pham_admin);
            ivHinh_anh_san_pham_adminC = itemView.findViewById(R.id.ivHinh_anh_san_pham_admin);
            iv_xoa_san_pham_adminC = itemView.findViewById(R.id.iv_xoa_san_pham_admin);
        }
    }
}
