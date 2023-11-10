package com.example.ban_dt_dapm.Cac_Loai_San_Pham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_recyclerview;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_san_pham;
import com.example.ban_dt_dapm.databinding.ActivityLaptopBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Laptop extends DrawerBaseActivity {
    ActivityLaptopBinding activityLaptopBinding;
    RecyclerView rvListC;
    ArrayList<get_set_san_pham> lstGetSetC;
    adapter_recyclerview adapter_recyclerviewC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLaptopBinding = ActivityLaptopBinding.inflate(getLayoutInflater());
        setContentView(activityLaptopBinding.getRoot());
        allocateActivityTitle("Trang Bán Laptop");
        //
        rvListC = findViewById(R.id.rvList1);
        lstGetSetC = new ArrayList<>();
        adapter_recyclerviewC = new adapter_recyclerview(lstGetSetC,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Laptop.this,2);
        rvListC.setAdapter(adapter_recyclerviewC);
        rvListC.setLayoutManager(gridLayoutManager);
        //
        AnhXa();
    }

    private void AnhXa() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lapTopRef = databaseRef.child("Lap_Top");
        lapTopRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String tenSP = dataSnapshot.child("tenSP").getValue(String.class);
                    String giaTien = dataSnapshot.child("giaTien").getValue(String.class);
                    String hinhAnh = dataSnapshot.child("hinhAnh").getValue(String.class);
                    get_set_san_pham sanPham = new get_set_san_pham(tenSP, "", giaTien, hinhAnh);
                    lstGetSetC.add(sanPham);
                }
                adapter_recyclerviewC.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}