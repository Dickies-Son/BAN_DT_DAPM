package com.example.ban_dt_dapm.Quan_Tri_Vien;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ban_dt_dapm.Cac_Loai_San_Pham.Laptop;
import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.Xy_Ly_Danh_Sach.get_set_san_pham;
import com.example.ban_dt_dapm.databinding.ActivityQuanLySanPhamBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;

public class QuanLySanPham extends DrawerBaseActivity {
    ActivityQuanLySanPhamBinding activityQuanLySanPhamBinding;
    ImageView iv_anh_san_phamC;
    Button button_chon_anh_san_phamC, button_luu_san_phamC;
    EditText tv_ten_san_phamC,tv_gia_san_phamC,tv_mo_ta_san_phamC;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    Spinner spinner_pathC;
    Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuanLySanPhamBinding = ActivityQuanLySanPhamBinding.inflate(getLayoutInflater());
        setContentView(activityQuanLySanPhamBinding.getRoot());
        allocateActivityTitle("Them san pham");
        //
        iv_anh_san_phamC =findViewById(R.id.iv_anh_san_pham);
        button_chon_anh_san_phamC = findViewById(R.id.button_chon_anh_san_pham);
        button_luu_san_phamC = findViewById(R.id.button_luu_san_pham);
        tv_gia_san_phamC = findViewById(R.id. tv_gia_san_pham);
        tv_ten_san_phamC = findViewById(R.id.tv_ten_san_pham);
        tv_mo_ta_san_phamC = findViewById(R.id.tv_mo_ta_san_pham);
        //
        //gia theo vnd
        tv_gia_san_phamC.addTextChangedListener(new TextWatcher() {
            String current = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals(current)) {
                    tv_gia_san_phamC.removeTextChangedListener(this);

                    String cleanString = editable.toString().replaceAll("[,.]", "");
                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.0;
                    }

                    DecimalFormat formatter = new DecimalFormat("#,###.###");
                    String formatted = formatter.format(parsed);
                    formatted = formatted.replace(",", ".");
                    current = formatted;

                    tv_gia_san_phamC.setText(formatted);
                    tv_gia_san_phamC.setSelection(formatted.length());
                    tv_gia_san_phamC.addTextChangedListener(this);
                }
            }
        });
        // spinner
        spinner_pathC = findViewById(R.id.spinner_path);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.paths_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pathC.setAdapter(adapter);
        //
        button_chon_anh_san_phamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                imagePickerLauncher.launch(intent);
            }
        });
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent intent = result.getData();
                selectedImageUri = intent.getData();
                // Hiển thị hình ảnh đã chọn trong ImageView
                iv_anh_san_phamC.setImageURI(selectedImageUri);
            }
        });
        button_luu_san_phamC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuanLySanPham.this);
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có chắc chắn muốn tải lên ảnh?");
                    builder.setPositiveButton("Đồng ý", (dialog, which) -> {

                        // Tạo một tham chiếu đến Firebase Storage
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        // Tạo một tham chiếu con với tên duy nhất cho hình ảnh
                        String imageName = "image_" + System.currentTimeMillis() + ".jpg";
                        StorageReference imageRef = storageRef.child("Hinh_Anh/" + imageName);
                        // Tải lên hình ảnh lên Firebase Storage
                        imageRef.putFile(selectedImageUri)
                                .addOnSuccessListener(taskSnapshot -> {
                                    // Lấy URL tải xuống của hình ảnh
                                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                        //
                                        String imageURL = uri.toString();
                                        // Lưu URL hình ảnh vào Firebase Realtime Database
                                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                                        DatabaseReference pathRef;
                                        String path = spinner_pathC.getSelectedItem().toString();
                                        String tenSP= tv_ten_san_phamC.getText().toString();
                                        String giaSP = tv_gia_san_phamC.getText().toString();
                                        String motaSP = tv_mo_ta_san_phamC.getText().toString();
                                        if (path.equals("Lap Top")) {
                                            pathRef = databaseRef.child("Lap_Top");
                                            pathRef.child(tenSP).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    get_set_san_pham get_set_san_pham = new get_set_san_pham(tenSP,motaSP,giaSP,imageURL,"","laptop");
                                                    pathRef.child(tenSP).setValue(get_set_san_pham);
                                                    Toast.makeText(QuanLySanPham.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(QuanLySanPham.this, Laptop.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        } else if (path.equals("Điện Thoại")) {
                                            pathRef = databaseRef.child("Dien_Thoai");
                                            pathRef.child(tenSP).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    get_set_san_pham get_set_san_pham = new get_set_san_pham(tenSP,motaSP,giaSP,imageURL,"","dienthoai");
                                                    pathRef.child(tenSP).setValue(get_set_san_pham);
                                                    Toast.makeText(QuanLySanPham.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(QuanLySanPham.this, Laptop.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        } else if (path.equals("Loa")) {
                                            pathRef = databaseRef.child("Loa");
                                            pathRef.child(tenSP).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    get_set_san_pham get_set_san_pham = new get_set_san_pham(tenSP,motaSP,giaSP,imageURL,"","loa");
                                                    pathRef.child(tenSP).setValue(get_set_san_pham);
                                                    Toast.makeText(QuanLySanPham.this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(QuanLySanPham.this, Laptop.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    });
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(QuanLySanPham.this, "Tải ảnh không thành công", Toast.LENGTH_SHORT).show();
                                });
                    });
                    builder.setNegativeButton("Hủy", (dialog, which) -> {
                        Toast.makeText(QuanLySanPham.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(QuanLySanPham.this, "Vui lòng chọn hình ảnh trước khi tải lên", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}