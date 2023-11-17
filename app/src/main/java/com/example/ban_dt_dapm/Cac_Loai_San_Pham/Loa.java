package com.example.ban_dt_dapm.Cac_Loai_San_Pham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_danh_sach_san_pham_admin;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_recyclerview;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.chi_tiet_san_pham;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_san_pham;
import com.example.ban_dt_dapm.databinding.ActivityLoaBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Loa extends DrawerBaseActivity implements adapter_recyclerview.UserCallback {
    ActivityLoaBinding activityLoaBinding;
    RecyclerView rvListC;
    ArrayList<get_set_san_pham> lstGetSetC;
    adapter_recyclerview adapter_recyclerviewC;
    adapter_danh_sach_san_pham_admin adapter_recyclerview_1C;
    SharedPreferences sharedPreferences_admin;
    SharedPreferences sharedPreferences_ten_nguoi_dung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoaBinding = ActivityLoaBinding.inflate(getLayoutInflater());
        setContentView(activityLoaBinding.getRoot());
        allocateActivityTitle("Trang Bán Loa");
        //
        rvListC = findViewById(R.id.rvList3);
        sharedPreferences_ten_nguoi_dung = getSharedPreferences("ten_nguoi_dung",MODE_PRIVATE);
        String tennguoidung = sharedPreferences_ten_nguoi_dung.getString("ten_nguoi_dung","");
        sharedPreferences_admin = getSharedPreferences("ten_nguoi_dung_admin",MODE_PRIVATE);
        String tennguoidung_admin = sharedPreferences_admin.getString("ten_nguoi_dung_admin","");

        if (tennguoidung_admin.isEmpty()){
            lstGetSetC = new ArrayList<>();
            adapter_recyclerviewC = new adapter_recyclerview(lstGetSetC,this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(Loa.this,2);
            rvListC.setAdapter(adapter_recyclerviewC);
            rvListC.setLayoutManager(gridLayoutManager);
        }else {
            lstGetSetC = new ArrayList<>();
            adapter_recyclerview_1C = new adapter_danh_sach_san_pham_admin(lstGetSetC,this);
            LinearLayoutManager linearLayoutManager =new LinearLayoutManager(Loa.this,LinearLayoutManager.VERTICAL,false);
            rvListC.setAdapter(adapter_recyclerview_1C);
            rvListC.setLayoutManager(linearLayoutManager);
        }
        //
        AnhXa();
    }

    private void AnhXa() {
        sharedPreferences_ten_nguoi_dung = getSharedPreferences("ten_nguoi_dung",MODE_PRIVATE);
        String tennguoidung = sharedPreferences_ten_nguoi_dung.getString("ten_nguoi_dung","");
        sharedPreferences_admin = getSharedPreferences("ten_nguoi_dung_admin",MODE_PRIVATE);
        String tennguoidung_admin = sharedPreferences_admin.getString("ten_nguoi_dung_admin","");
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lapTopRef = databaseRef.child("Loa");
        lapTopRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String tenSP = dataSnapshot.child("tenSP").getValue(String.class);
                    String giaTien = dataSnapshot.child("giaTien").getValue(String.class);
                    String hinhAnh = dataSnapshot.child("hinhAnh").getValue(String.class);
                    String motaSP = dataSnapshot.child("moTa").getValue(String.class);
                    String idSP = dataSnapshot.child("idSP").getValue(String.class);
                    get_set_san_pham sanPham = new get_set_san_pham(tenSP, motaSP, giaTien, hinhAnh,"","",idSP);
                    lstGetSetC.add(sanPham);
                }
                if (tennguoidung_admin.isEmpty()){
                    adapter_recyclerviewC.notifyDataSetChanged();
                }else {
                    adapter_recyclerview_1C.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(String giaTien, String hinhAnh, String tenSP, String moTa, String idSP) {
        Intent i = new Intent(Loa.this, chi_tiet_san_pham.class);
        i.putExtra("giaTien",giaTien);
        i.putExtra("hinhAnh",hinhAnh);
        i.putExtra("tenSP",tenSP);
        i.putExtra("moTa",moTa);
        i.putExtra("idSP", idSP);
        startActivity(i);
    }
}