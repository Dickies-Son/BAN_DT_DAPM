package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ban_dt_dapm.Nguoi_Dung.DatHang;
import com.example.ban_dt_dapm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_gio_hang extends RecyclerView.Adapter<adapter_gio_hang.UserViewHolder> {
    ArrayList<get_set_san_pham> lstGetSet;
    Context context;
    SharedPreferences sharedPreferences_so_dien_thoai_nguoi_dung;

    public adapter_gio_hang(ArrayList<get_set_san_pham> lstGetSet, Context context) {
        this.lstGetSet = lstGetSet;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter_gio_hang.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nap layout
        View userView = inflater.inflate(R.layout.item_gio_hang,parent,false);
        //
        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_gio_hang.UserViewHolder holder, int position) {
        get_set_san_pham item = lstGetSet.get(position);
        holder.tvTen_spC.setText(item.getTenSP());
        holder.tvGia_spC.setText(item.getGiaTien());
        holder.tvSoluong_spC.setText(item.getSoLuong());
        Picasso.get().load(item.getHinhAnh()).into(holder.ivHinh_anh_san_phamC);
        holder.button_tangC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(holder.tvSoluong_spC.getText().toString());
                soLuong++;
                holder.tvSoluong_spC.setText(String.valueOf(soLuong));
                item.setSoLuong(String.valueOf(soLuong));
                // Cập nhật số lượng sản phẩm lên firebase
                sharedPreferences_so_dien_thoai_nguoi_dung = context.getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
                String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung","");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference gioHangRef = databaseReference.child("Nguoi_Dung")
                        .child(sodienthoai)
                        .child("GioHang")
                        .child(item.getIdSP())
                        .child("soLuong");
                // Cập nhật số lượng sản phẩm trên Firebase
                gioHangRef.setValue(String.valueOf(soLuong));
            }
        });
        holder.button_giamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(holder.tvSoluong_spC.getText().toString());
                if (soLuong > 1) {
                    soLuong--;
                    holder.tvSoluong_spC.setText(String.valueOf(soLuong));
                    item.setSoLuong(String.valueOf(soLuong));
                    // Cập nhật số lượng sản phẩm lên firebase
                    sharedPreferences_so_dien_thoai_nguoi_dung = context.getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
                    String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung","");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference gioHangRef = databaseReference.child("Nguoi_Dung")
                            .child(sodienthoai)
                            .child("GioHang")
                            .child(item.getIdSP())
                            .child("soLuong");
                    // Cập nhật số lượng sản phẩm trên Firebase
                    gioHangRef.setValue(String.valueOf(soLuong));
                }
            }
        });
        holder.iv_xoa_san_phamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    get_set_san_pham item = lstGetSet.get(position);
                    sharedPreferences_so_dien_thoai_nguoi_dung = context.getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
                    String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung", "");
                    lstGetSet.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, lstGetSet.size());
                    DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference()
                            .child("Nguoi_Dung")
                            .child(sodienthoai)
                            .child("GioHang").child(item.getIdSP());
                    gioHangRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Xóa thành công, cập nhật giao diện và hiển thị thông báo
                            Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi xóa thất bại
                            Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        holder.iv_dat_hangC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DatHang.class);
                // Put the data you want to pass as extras
                intent.putExtra("tenSP_dat_hang", item.getTenSP());
                intent.putExtra("giaTien_dat_hang", item.getGiaTien());
                intent.putExtra("hinhAnh_dat_hang",item.getHinhAnh());
                intent.putExtra("soLuong_dat_hang", item.getSoLuong());
                intent.putExtra("idSP_dat_hang",item.getIdSP());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstGetSet.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinh_anh_san_phamC,button_giamC,button_tangC;
        TextView tvTen_spC,tvGia_spC,tvSoluong_spC;
        ImageView iv_xoa_san_phamC,iv_dat_hangC;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh_anh_san_phamC = itemView.findViewById(R.id.ivHinh_anh_san_pham);
            tvTen_spC = itemView.findViewById(R.id.tvTen_sp);
            tvGia_spC = itemView.findViewById(R.id.tvGia_sp);
            tvSoluong_spC = itemView.findViewById(R.id.tvSoluong_sp);
            iv_xoa_san_phamC = itemView.findViewById(R.id.iv_xoa_san_pham);
            iv_dat_hangC = itemView.findViewById(R.id.iv_dat_hang);
            button_giamC = itemView.findViewById(R.id.button_giam);
            button_tangC = itemView.findViewById(R.id.button_tang);
        }
    }
}
