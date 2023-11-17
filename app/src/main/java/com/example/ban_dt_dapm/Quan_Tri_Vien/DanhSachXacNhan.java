package com.example.ban_dt_dapm.Quan_Tri_Vien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_danh_sach_dat_hang_admin;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_danh_sach_xac_nhan_admin;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_dat_hang;
import com.example.ban_dt_dapm.databinding.ActivityDanhSachXacNhanBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachXacNhan extends DrawerBaseActivity {
    ActivityDanhSachXacNhanBinding activityDanhSachXacNhanBinding;
    RecyclerView rvListC;
    ArrayList<get_set_dat_hang> lstGetSetC;
    adapter_danh_sach_xac_nhan_admin adapter_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDanhSachXacNhanBinding = ActivityDanhSachXacNhanBinding.inflate(getLayoutInflater());
        setContentView(activityDanhSachXacNhanBinding.getRoot());
        allocateActivityTitle("Danh sách đơn hàng");
        //
        Anhxa();
        //
        rvListC = findViewById(R.id.rvList_danh_sach_xac_nhan_admin);
        lstGetSetC = new ArrayList<>();
        adapter_recyclerview = new adapter_danh_sach_xac_nhan_admin(lstGetSetC,this);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(DanhSachXacNhan.this,LinearLayoutManager.VERTICAL,false);
        rvListC.setAdapter(adapter_recyclerview);
        rvListC.setLayoutManager(linearLayoutManager);
    }
    private void Anhxa() {
        DatabaseReference nguoiDungRef = FirebaseDatabase.getInstance().getReference("Nguoi_Dung");
        nguoiDungRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot soDienThoaiSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot datHangSnapshot : soDienThoaiSnapshot.child("DaXacNhan").getChildren()) {
                        String ten = datHangSnapshot.child("ten").getValue(String.class);
                        String tenSP = datHangSnapshot.child("tenSP").getValue(String.class);
                        String so_dien_thoai = datHangSnapshot.child("so_dien_thoai").getValue(String.class);
                        String giaTien = datHangSnapshot.child("giaTien").getValue(String.class);
                        String dia_chi = datHangSnapshot.child("dia_chi").getValue(String.class);
                        String hinhAnh = datHangSnapshot.child("hinhAnh").getValue(String.class);
                        String moTa = datHangSnapshot.child("moTa").getValue(String.class);
                        String soLuong = datHangSnapshot.child("soLuong").getValue(String.class);
                        String idSP = datHangSnapshot.child("idSP").getValue(String.class);

                        get_set_dat_hang nguoiDung = new get_set_dat_hang(ten, so_dien_thoai, "", dia_chi, tenSP, moTa, giaTien, "", hinhAnh, soLuong, idSP);
                        lstGetSetC.add(nguoiDung);
                        // Log.d("FirebaseData", "Data: " + lstGetSetC.toString());
                    }
                }
                adapter_recyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}