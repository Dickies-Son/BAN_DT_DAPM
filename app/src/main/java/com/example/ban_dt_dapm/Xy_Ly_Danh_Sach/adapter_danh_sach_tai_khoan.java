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

import java.util.ArrayList;

public class adapter_danh_sach_tai_khoan extends RecyclerView.Adapter<adapter_danh_sach_tai_khoan.UserViewHolder> {
    ArrayList<get_set_nguoi_dung> lstGetSet;
    Context context;

    public adapter_danh_sach_tai_khoan(ArrayList<get_set_nguoi_dung> lstGetSet, Context context) {
        this.lstGetSet = lstGetSet;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter_danh_sach_tai_khoan.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nap layout
        View userView = inflater.inflate(R.layout.item_nguoi_dung,parent,false);
        //
        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_danh_sach_tai_khoan.UserViewHolder holder, int position) {
        get_set_nguoi_dung item = lstGetSet.get(position);
        //
        holder.tvTen_nguoi_dungC.setText(item.getTen());
        holder.tvSdt_nguoi_dungC.setText(item.getSo_dien_thoai());
        holder.tvMat_khau_nguoi_dungC.setText(item.getMat_khau());
        //xoa tai khoan nguoi dung
        holder.iv_xoa_tai_khoanC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xóa tài khoản tại vị trí hiện tại trong ArrayList
                                    get_set_nguoi_dung item = lstGetSet.get(position);
                                    lstGetSet.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, lstGetSet.size());

                                    // Xóa tài khoản tương ứng trong Firebase Realtime Database
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Nguoi_Dung").child(item.getSo_dien_thoai());
                                    ref.removeValue();

                                    Toast.makeText(context, "Đã xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
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
        TextView tvTen_nguoi_dungC,tvSdt_nguoi_dungC,tvMat_khau_nguoi_dungC;
        ImageView iv_xoa_tai_khoanC,ivHinh_anh_nguoi_dungC;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen_nguoi_dungC = itemView.findViewById(R.id.tvTen_nguoi_dung);
            tvSdt_nguoi_dungC = itemView.findViewById(R.id.tvSdt_nguoi_dung);
            tvMat_khau_nguoi_dungC = itemView.findViewById(R.id.tvMat_khau_nguoi_dung);
            iv_xoa_tai_khoanC = itemView.findViewById(R.id.iv_xoa_tai_khoan);
            ivHinh_anh_nguoi_dungC = itemView.findViewById(R.id.ivHinh_anh_nguoi_dung);
        }
    }
}
