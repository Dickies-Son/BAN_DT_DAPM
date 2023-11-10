package com.example.ban_dt_dapm.Nguoi_Dung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_dat_hang;
import com.example.ban_dt_dapm.databinding.ActivityDatHangBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DatHang extends DrawerBaseActivity {
    ActivityDatHangBinding activityDatHangBinding;
    ImageView ivHinh_anh_san_pham_dat_hangC;
    TextView tvTen_sp_dat_hangC,tvGia_sp_dat_hangC,tvSoluong_sp_dat_hangC;
    TextView tv_TenNguoiDung_layout_dathangC,tv_DiaChi_layout_dathangC,tv_SoDienThoai_layout_dathangC;
    TextView tv_Tong_tienC;
    Button button_layout_dat_hangC;
    SharedPreferences sharedPreferences_ten_nguoi_dung;
    SharedPreferences sharedPreferences_dia_chi_nguoi_dung;
    SharedPreferences sharedPreferences_so_dien_thoai_nguoi_dung;
    SharedPreferences sharedPreferences_mat_khau_nguoi_dung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDatHangBinding = ActivityDatHangBinding.inflate(getLayoutInflater());
        setContentView(activityDatHangBinding.getRoot());
        allocateActivityTitle("Đặt sản phẩm");
        //
        ivHinh_anh_san_pham_dat_hangC = findViewById(R.id.ivHinh_anh_san_pham_dat_hang);
        tvTen_sp_dat_hangC = findViewById(R.id.tvTen_sp_dat_hang);
        tvGia_sp_dat_hangC = findViewById(R.id.tvGia_sp_dat_hang);
        tvSoluong_sp_dat_hangC = findViewById(R.id.tvSoluong_sp_dat_hang);
        tv_TenNguoiDung_layout_dathangC = findViewById(R.id.tv_TenNguoiDung_layout_dathang);
        tv_DiaChi_layout_dathangC = findViewById(R.id.tv_DiaChi_layout_dathang);
        tv_SoDienThoai_layout_dathangC = findViewById(R.id.tv_SoDienThoai_layout_dathang);
        tv_Tong_tienC = findViewById(R.id.tv_Tong_tien);
        button_layout_dat_hangC = findViewById(R.id.button_layout_dat_hang);
        //
        Intent i = getIntent();
        String giaTien = i.getStringExtra("giaTien_dat_hang");
        String hinhAnh = i.getStringExtra("hinhAnh_dat_hang");
        String tenSP = i.getStringExtra("tenSP_dat_hang");
        String soLuong = i.getStringExtra("soLuong_dat_hang");
        tvGia_sp_dat_hangC.setText(giaTien);
        tvTen_sp_dat_hangC.setText(tenSP);
        tvSoluong_sp_dat_hangC.setText(soLuong);
        Picasso.get().load(hinhAnh).into(ivHinh_anh_san_pham_dat_hangC);
        // Loại bỏ dấu phân cách hàng nghìn từ chuỗi giá tiền
        String giaTienCleaned = giaTien.replaceAll("[,.]", "");
        // Chuyển đổi giá tiền thành kiểu số
        double giaTienDouble;
        try {
            giaTienDouble = Double.parseDouble(giaTienCleaned);
        } catch (NumberFormatException e) {
            giaTienDouble = 0.0;
        }
        // Chuyển đổi giá tiền và số lượng thành kiểu số
        int soLuongInt = Integer.parseInt(soLuong);
        double tongTien = giaTienDouble * soLuongInt;
        DecimalFormat formatter = new DecimalFormat("#,###.###");
        String formattedTongTien = formatter.format(tongTien);
        formattedTongTien = formattedTongTien.replace(",", ".");
        tv_Tong_tienC.setText(formattedTongTien);
        //
        sharedPreferences_ten_nguoi_dung = getSharedPreferences("ten_nguoi_dung",MODE_PRIVATE);
        String tennguoidung = sharedPreferences_ten_nguoi_dung.getString("ten_nguoi_dung","");
        tv_TenNguoiDung_layout_dathangC.setText(tennguoidung);

        sharedPreferences_so_dien_thoai_nguoi_dung = getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
        String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung","");
        tv_SoDienThoai_layout_dathangC.setText(sodienthoai);

        sharedPreferences_dia_chi_nguoi_dung = getSharedPreferences("dia_chi_nguoi_dung",MODE_PRIVATE);
        String diachinguoidung = sharedPreferences_dia_chi_nguoi_dung.getString("dia_chi_nguoi_dung","");
        tv_DiaChi_layout_dathangC.setText(diachinguoidung);
        //
        button_layout_dat_hangC.setOnClickListener(new View.OnClickListener() {
            String giaTienC = tv_Tong_tienC.getText().toString();
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Nguoi_Dung");
                Query kiemtra = reference.orderByChild("so_dien_thoai").equalTo(sodienthoai);
                kiemtra.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Kiểm tra xem dữ liệu có tồn tại hay không
                        if (snapshot.exists()) {
                            String sodienthoaiaa = snapshot.child(sodienthoai).child("so_dien_thoai").getValue(String.class);
                            // Thực hiện các bước tiếp theo để thêm sản phẩm vào giỏ hàng
                            DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference().child("Nguoi_Dung")
                                    .child(sodienthoaiaa)
                                    .child("DatHang");
                            // Tạo đối tượng SanPham
                            get_set_dat_hang sanPham = new get_set_dat_hang(tennguoidung,sodienthoai,"",diachinguoidung,tenSP,"",giaTienC,"",hinhAnh,soLuong);
                            sanPham.setMoTa("Chờ Thanh Toán");
                            // Thêm sản phẩm vào giỏ hàng
                            gioHangRef.child(tenSP).setValue(sanPham)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(DatHang.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DatHang.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            Toast.makeText(DatHang.this, "Bạn hãy đăng ký hoặc đăng nhập nhé", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}