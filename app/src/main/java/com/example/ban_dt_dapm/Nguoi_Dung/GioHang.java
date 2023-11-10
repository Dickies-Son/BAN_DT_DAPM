package com.example.ban_dt_dapm.Nguoi_Dung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_gio_hang;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_san_pham;
import com.example.ban_dt_dapm.databinding.ActivityGioHangBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GioHang extends DrawerBaseActivity {
    ActivityGioHangBinding activityGioHangBinding;
    RecyclerView rvListC;
    ArrayList<get_set_san_pham> lstGetSetC;
    adapter_gio_hang adapter_recyclerviewC;
    SharedPreferences sharedPreferences_so_dien_thoai_nguoi_dung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGioHangBinding = ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(activityGioHangBinding.getRoot());
        allocateActivityTitle("Giỏ hàng của bạn");
        //
        rvListC = findViewById(R.id.rvList3);
        lstGetSetC = new ArrayList<>();
        adapter_recyclerviewC = new adapter_gio_hang(lstGetSetC,this);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(GioHang.this,LinearLayoutManager.VERTICAL,false);
        rvListC.setAdapter(adapter_recyclerviewC);
        rvListC.setLayoutManager(linearLayoutManager);
        //
        AnhXa();
    }
    private void AnhXa() {
        sharedPreferences_so_dien_thoai_nguoi_dung = getSharedPreferences("sdt_nguoi_dung", MODE_PRIVATE);
        String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung", "");
        DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference().child("Nguoi_Dung")
                .child(sodienthoai)
                .child("GioHang");
        gioHangRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstGetSetC.clear(); // Xóa danh sách hiện tại để làm mới dữ liệu
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenSP = snapshot.child("tenSP").getValue(String.class);
                    String giaTien = snapshot.child("giaTien").getValue(String.class);
                    String hinhAnh = snapshot.child("hinhAnh").getValue(String.class);
                    String moTa = snapshot.child("moTa").getValue(String.class);
                    String soLuong = snapshot.child("soLuong").getValue(String.class);
                    get_set_san_pham sanPham = new get_set_san_pham(tenSP,moTa,giaTien,hinhAnh,soLuong,"");
                    lstGetSetC.add(sanPham);
                }
                adapter_recyclerviewC.notifyDataSetChanged(); // Cập nhật RecyclerView khi có thay đổi dữ liệu
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }


        });
    }
}