package com.example.ban_dt_dapm.Nguoi_Dung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_nguoi_dung;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangKy extends AppCompatActivity {
    EditText username_dang_kyC,sdt_dang_kyC,mat_khau_dang_kyC,re_mat_khau_dang_kyC;
    Button button_dangKyC,button_da_co_tai_khoan_dang_nhapC;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        username_dang_kyC = findViewById(R.id.username_dang_ky);
        sdt_dang_kyC = findViewById(R.id.sdt_dang_ky);
        mat_khau_dang_kyC = findViewById(R.id.mat_khau_dang_ky);
        re_mat_khau_dang_kyC = findViewById(R.id.re_mat_khau_dang_ky);
        button_dangKyC = findViewById(R.id.button_dangKy);
        button_da_co_tai_khoan_dang_nhapC = findViewById(R.id.da_co_tai_khoan_dang_nhap);
        button_dangKyC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Nguoi_Dung");

                String ten = username_dang_kyC.getText().toString();
                String so_dien_thoai = sdt_dang_kyC.getText().toString();
                String mat_khau = mat_khau_dang_kyC.getText().toString();
                String re_mat_khau = re_mat_khau_dang_kyC.getText().toString();

                if (TextUtils.isEmpty(ten)) {
                    username_dang_kyC.setError("Hãy nhập tên đầy đủ");
                    Toast.makeText(DangKy.this, "Nhập tên vào đi nè", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (so_dien_thoai.length() != 10) {
                    sdt_dang_kyC.setError("Số điện thoại phải là 10 chữ số");
                    Toast.makeText(DangKy.this, "Số điện thoại phải là 10 chữ nha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mat_khau.length() < 6) {
                    mat_khau_dang_kyC.setError("Mật khẩu tối thiểu 6 ký tự");
                    Toast.makeText(DangKy.this, "Mật khẩu tối thiểu 6 ký tự nha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mat_khau.equals(re_mat_khau)) {
                    re_mat_khau_dang_kyC.setError("Mật khẩu nhập lại không trùng khớp");
                    Toast.makeText(DangKy.this, "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }


                reference.child(so_dien_thoai).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Số điện thoại đã tồn tại, hiển thị thông báo lỗi
                            sdt_dang_kyC.setError("Số điện thoại đã được đăng ký");
                            Toast.makeText(DangKy.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                        } else {
                            // Số điện thoại chưa tồn tại, thêm thông tin người dùng mới vào Firebase Realtime Database
                            get_set_nguoi_dung nguoi_dung = new get_set_nguoi_dung(ten, so_dien_thoai, mat_khau);
                            reference.child(so_dien_thoai).setValue(nguoi_dung);
                            Toast.makeText(DangKy.this, "Chúc mừng bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKy.this, DangNhap.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        button_da_co_tai_khoan_dang_nhapC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this,DangNhap.class);
                startActivity(intent);
            }
        });
    }
}