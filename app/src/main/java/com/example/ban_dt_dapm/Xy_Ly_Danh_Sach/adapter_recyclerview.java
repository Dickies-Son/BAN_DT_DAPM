package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ban_dt_dapm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_recyclerview extends RecyclerView.Adapter<adapter_recyclerview.UserViewHolder> {
    ArrayList<get_set_san_pham> lstGetSet;
    Context context;
    UserCallback userCallback;

    public adapter_recyclerview(ArrayList<get_set_san_pham> lstGetSet, UserCallback userCallback) {
        this.lstGetSet = lstGetSet;
        this.userCallback = userCallback;
    }

    @NonNull
    @Override
    public adapter_recyclerview.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nap layout
        View userView = inflater.inflate(R.layout.item_san_pham,parent,false);
        //
        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_recyclerview.UserViewHolder holder, int position) {
        get_set_san_pham item = lstGetSet.get(position);
        holder.Tv_TenSanPhamC.setText(item.getTenSP());
        holder.Tv_GiaSanPhamC.setText(item.getGiaTien());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCallback.onItemClick(item.getGiaTien(),item.getHinhAnh(),item.getTenSP(),item.getMoTa());
            }
        });
        Picasso.get().load(item.getHinhAnh()).into(holder.Iv_HinhAnhSanPhamC);
    }

    @Override
    public int getItemCount() {
        return lstGetSet.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView Tv_TenSanPhamC,Tv_GiaSanPhamC;
        ImageView Iv_HinhAnhSanPhamC;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_GiaSanPhamC = itemView.findViewById(R.id.Tv_GiaSanPham);
            Tv_TenSanPhamC = itemView.findViewById(R.id.Tv_TenSanPham);
            Iv_HinhAnhSanPhamC =itemView.findViewById(R.id.Iv_HinhAnhSanPham);
        }
    }
    public interface UserCallback{
        void onItemClick(String giaTien, String hinhAnh, String tenSP, String moTa);
    }
}
