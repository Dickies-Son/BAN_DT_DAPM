package com.example.ban_dt_dapm.Nguoi_Dung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ban_dt_dapm.Cac_Loai_San_Pham.DienThoai;
import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.adapter_danh_sach_dat_hang;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_dat_hang;
import com.example.ban_dt_dapm.databinding.ActivityNguoiDungBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NguoiDung extends DrawerBaseActivity {
    ActivityNguoiDungBinding activityNguoiDungBinding;
    TextView tv_TenNguoiDung_layout_nguoidung_1C,tv_TenNguoiDung_layout_nguoidungC,tv_DiaChi_layout_nguoidungC,tv_SoDienThoai_layout_nguoidungC;
    Button button_dangxuat_layout_nguoidungC,button_chinhsua_layout_nguoidungC;
    RelativeLayout relativeLayout_1C,relativeLayout_2C;
    SharedPreferences sharedPreferences_admin;
    SharedPreferences sharedPreferences_ten_nguoi_dung;
    SharedPreferences sharedPreferences_dia_chi_nguoi_dung;
    SharedPreferences sharedPreferences_so_dien_thoai_nguoi_dung;
    SharedPreferences sharedPreferences_mat_khau_nguoi_dung;
    RecyclerView rvListC;
    ArrayList<get_set_dat_hang> lstGetSetC;
    adapter_danh_sach_dat_hang adapter_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNguoiDungBinding = ActivityNguoiDungBinding.inflate(getLayoutInflater());
        setContentView(activityNguoiDungBinding.getRoot());
        allocateActivityTitle("Cá nhân");
        //
        relativeLayout_1C = findViewById(R.id.layout_relative_1);
        relativeLayout_2C = findViewById(R.id.layout_relative_2);
        tv_TenNguoiDung_layout_nguoidung_1C = findViewById(R.id.tv_TenNguoiDung_layout_nguoidung_1);
        tv_TenNguoiDung_layout_nguoidungC = findViewById(R.id.tv_TenNguoiDung_layout_nguoidung);
        tv_DiaChi_layout_nguoidungC = findViewById(R.id.tv_DiaChi_layout_nguoidung);
        tv_SoDienThoai_layout_nguoidungC = findViewById(R.id.tv_SoDienThoai_layout_nguoidung);
        button_dangxuat_layout_nguoidungC = findViewById(R.id.button_dangxuat_layout_nguoidung);
        button_chinhsua_layout_nguoidungC = findViewById(R.id.button_chinhsua_layout_nguoidung);
        //
        AnhXa();
        //
        rvListC = findViewById(R.id.rvList_danh_sach_dat_hang_nguoi_dung);
        lstGetSetC = new ArrayList<>();
        adapter_recyclerview = new adapter_danh_sach_dat_hang(lstGetSetC,this);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(NguoiDung.this,LinearLayoutManager.HORIZONTAL,false);
        rvListC.setAdapter(adapter_recyclerview);
        rvListC.setLayoutManager(linearLayoutManager);
        //
        sharedPreferences_ten_nguoi_dung = getSharedPreferences("ten_nguoi_dung",MODE_PRIVATE);
        String tennguoidung = sharedPreferences_ten_nguoi_dung.getString("ten_nguoi_dung","");

        sharedPreferences_admin = getSharedPreferences("ten_nguoi_dung_admin",MODE_PRIVATE);
        String tennguoidung_admin = sharedPreferences_admin.getString("ten_nguoi_dung_admin","");

        sharedPreferences_so_dien_thoai_nguoi_dung = getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
        String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung","");
        tv_SoDienThoai_layout_nguoidungC.setText(sodienthoai);

        sharedPreferences_dia_chi_nguoi_dung = getSharedPreferences("dia_chi_nguoi_dung",MODE_PRIVATE);
        String diachinguoidung = sharedPreferences_dia_chi_nguoi_dung.getString("dia_chi_nguoi_dung","");
        tv_DiaChi_layout_nguoidungC.setText(diachinguoidung);

        sharedPreferences_mat_khau_nguoi_dung = getSharedPreferences("mat_khau_nguoi_dung",MODE_PRIVATE);
        String matkhaunguoidung = sharedPreferences_mat_khau_nguoi_dung.getString("mat_khau_nguoi_dung","");

        if (tennguoidung_admin.isEmpty()){
            tv_TenNguoiDung_layout_nguoidung_1C.setText(tennguoidung);
            tv_TenNguoiDung_layout_nguoidungC.setText(tennguoidung);
        }else {
            tv_TenNguoiDung_layout_nguoidungC.setText(tennguoidung_admin);
            tv_TenNguoiDung_layout_nguoidung_1C.setText(tennguoidung_admin);
        }
        if (!tennguoidung.isEmpty()) {
            relativeLayout_2C.setVisibility(View.GONE);
        } else {
            if (!tennguoidung_admin.isEmpty()) {
                relativeLayout_2C.setVisibility(View.GONE);
            } else {
                relativeLayout_1C.setVisibility(View.GONE);
            }
        }

        button_dangxuat_layout_nguoidungC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //admin

                SharedPreferences.Editor editor1 = sharedPreferences_admin.edit();
                //
                editor1.remove("ten_nguoi_dung_admin");
                editor1.remove("loggedIn_admin");
                //người dùng
                SharedPreferences.Editor editor = sharedPreferences_ten_nguoi_dung.edit();
                SharedPreferences.Editor editor2 = sharedPreferences_dia_chi_nguoi_dung.edit();
                SharedPreferences.Editor editor3 = sharedPreferences_so_dien_thoai_nguoi_dung.edit();
                SharedPreferences.Editor editor4 = sharedPreferences_mat_khau_nguoi_dung.edit();
                //
                editor.remove("ten_nguoi_dung");
                editor.remove("loggedIn");
                editor2.remove("dia_chi_nguoi_dung");
                editor3.remove("sdt_nguoi_dung");
                editor3.remove("mat_khau_nguoi_dung");
                // apply
                editor.apply();
                editor1.apply();
                editor2.apply();
                editor3.apply();
                editor4.apply();
                //
                Toast.makeText(NguoiDung.this, "Bạn đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NguoiDung.this, DienThoai.class));
                finishAffinity();
            }
        });
    }
   // danh sach dat hang
    private void AnhXa() {
        sharedPreferences_so_dien_thoai_nguoi_dung = getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
        String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung","");
        // Lấy dữ liệu từ Firebase Realtime Database
        DatabaseReference nguoiDungRef = FirebaseDatabase.getInstance().getReference("Nguoi_Dung").child(sodienthoai);
        nguoiDungRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    DatabaseReference datHangRef = snapshot.child("DatHang").getRef();
                    datHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String tenSP = userSnapshot.child("tenSP").getValue(String.class);
                                String soLuong = userSnapshot.child("soLuong").getValue(String.class);
                                String giaTien = userSnapshot.child("giaTien").getValue(String.class);
                                String idSP = userSnapshot.child("idSP").getValue(String.class);
                                String hinhAnh = userSnapshot.child("hinhAnh").getValue(String.class);
                                String moTa = userSnapshot.child("moTa").getValue(String.class);
                                get_set_dat_hang nguoiDung = new get_set_dat_hang("","","","",tenSP,moTa,giaTien,"",hinhAnh,soLuong,idSP);
                                lstGetSetC.add(nguoiDung);
                                // Log.d("FirebaseData", "Data: " + lstGetSetC.toString());
                            }
                            adapter_recyclerview.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    DatabaseReference XacNhanRef = snapshot.child("DaXacNhan").getRef();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference nguoiDungRef1 = FirebaseDatabase.getInstance().getReference("Nguoi_Dung").child(sodienthoai);
        nguoiDungRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    DatabaseReference datHangRef = snapshot.child("DaXacNhan").getRef();
                    datHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String tenSP = userSnapshot.child("tenSP").getValue(String.class);
                                String soLuong = userSnapshot.child("soLuong").getValue(String.class);
                                String giaTien = userSnapshot.child("giaTien").getValue(String.class);
                                String idSP = userSnapshot.child("idSP").getValue(String.class);
                                String hinhAnh = userSnapshot.child("hinhAnh").getValue(String.class);
                                String moTa = userSnapshot.child("moTa").getValue(String.class);
                                get_set_dat_hang nguoiDung = new get_set_dat_hang("","","","",tenSP,moTa,giaTien,"",hinhAnh,soLuong,idSP);
                                lstGetSetC.add(nguoiDung);
                                // Log.d("FirebaseData", "Data: " + lstGetSetC.toString());
                            }
                            adapter_recyclerview.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    DatabaseReference XacNhanRef = snapshot.child("DaXacNhan").getRef();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}