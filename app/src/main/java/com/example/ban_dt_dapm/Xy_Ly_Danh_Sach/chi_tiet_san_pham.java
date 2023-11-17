package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

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
import com.example.ban_dt_dapm.databinding.ActivityChiTietSanPhamBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class chi_tiet_san_pham extends DrawerBaseActivity {
    ActivityChiTietSanPhamBinding activity_chi_tiet_san_pham;
    TextView mota_sp_chitietC, ten_sp_chitietC,gia_sp_chitietC;
    ImageView hinhanh_sp_chitietC;
    Button button_them_gio_hangC;
    SharedPreferences sharedPreferences_so_dien_thoai_nguoi_dung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_chi_tiet_san_pham = ActivityChiTietSanPhamBinding.inflate(getLayoutInflater());
        setContentView(activity_chi_tiet_san_pham.getRoot());
        allocateActivityTitle("Chi tiết sản phẩm");
        //
        hinhanh_sp_chitietC = findViewById(R.id.hinhanh_sp_chitiet);
        mota_sp_chitietC = findViewById(R.id.mota_sp_chitiet);
        ten_sp_chitietC = findViewById(R.id.ten_sp_chitiet);
        gia_sp_chitietC = findViewById(R.id.gia_sp_chitiet);
        button_them_gio_hangC = findViewById(R.id.button_them_gio_hang);
        // String giaTien, String hinhAnh, String tenSP, String moTa
        Intent i = getIntent();
        String giaTien = i.getStringExtra("giaTien");
        String hinhAnh = i.getStringExtra("hinhAnh");
        String tenSP = i.getStringExtra("tenSP");
        String moTa = i.getStringExtra("moTa");
        String idSP = i.getStringExtra("idSP");

        ten_sp_chitietC.setText(tenSP);
        gia_sp_chitietC.setText(giaTien);
        mota_sp_chitietC.setText(moTa);
        Picasso.get().load(hinhAnh).into(hinhanh_sp_chitietC);
        button_them_gio_hangC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences_so_dien_thoai_nguoi_dung = getSharedPreferences("sdt_nguoi_dung",MODE_PRIVATE);
                String sodienthoai = sharedPreferences_so_dien_thoai_nguoi_dung.getString("sdt_nguoi_dung","");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Nguoi_Dung");
                Query kiemtra = reference.orderByChild("so_dien_thoai").equalTo(sodienthoai);

                kiemtra.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Kiểm tra xem dữ liệu có tồn tại hay không
                        if (dataSnapshot.exists()) {
                            // Lấy số điện thoại từ dữ liệu
                            String sodienthoaiaa = dataSnapshot.child(sodienthoai).child("so_dien_thoai").getValue(String.class);
                            // Thực hiện các bước tiếp theo để thêm sản phẩm vào giỏ hàng
                            DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference().child("Nguoi_Dung")
                                    .child(sodienthoaiaa)
                                    .child("GioHang");
                            // Tạo đối tượng SanPham
                            get_set_san_pham sanPham = new get_set_san_pham(tenSP, moTa, giaTien,hinhAnh, "","",idSP);
                            sanPham.setSoLuong("1"); // Mặc định số lượng là 1
                            // Thêm sản phẩm vào giỏ hàng
                            gioHangRef.child(idSP).setValue(sanPham)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(chi_tiet_san_pham.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(chi_tiet_san_pham.this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            Toast.makeText(chi_tiet_san_pham.this, "Bạn hãy đăng ký hoặc đăng nhập nhé", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });
    }
}