package com.example.ban_dt_dapm.Xu_Ly_Giao_Dien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ban_dt_dapm.Cac_Loai_San_Pham.DienThoai;
import com.example.ban_dt_dapm.Cac_Loai_San_Pham.Laptop;
import com.example.ban_dt_dapm.Cac_Loai_San_Pham.Loa;
import com.example.ban_dt_dapm.Nguoi_Dung.DangKy;
import com.example.ban_dt_dapm.Nguoi_Dung.DangNhap;
import com.example.ban_dt_dapm.Nguoi_Dung.GioHang;
import com.example.ban_dt_dapm.Nguoi_Dung.NguoiDung;
import com.example.ban_dt_dapm.Quan_Tri_Vien.DanhSachDatHang;
import com.example.ban_dt_dapm.Quan_Tri_Vien.DanhSachTaiKhoan;
import com.example.ban_dt_dapm.Quan_Tri_Vien.DanhSachXacNhan;
import com.example.ban_dt_dapm.Quan_Tri_Vien.QuanLySanPham;
import com.example.ban_dt_dapm.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationViewC;
    Button btnDangNhapC, btnDangKyC;
    TextView ten_nguoi_dungC;
    LinearLayout layout_2C;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences_admin;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // doi mau` hambuger ( Them cho nay ) ######
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.black));
        //an StatusBar System
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //anh xa button va view navigation
        navigationViewC = findViewById(R.id.nav_View);
        View headerView = navigationViewC.getHeaderView(0);
        btnDangNhapC = headerView.findViewById(R.id.button_dangNhap_0);
        btnDangKyC = headerView.findViewById(R.id.button_dangKy_0);
        ten_nguoi_dungC = headerView.findViewById(R.id.ten_nguoi_dung);
        layout_2C = headerView.findViewById(R.id.layout_2);
        btnDangKyC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerBaseActivity.this, DangKy.class);
                startActivity(intent);
            }
        });
        btnDangNhapC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerBaseActivity.this, DangNhap.class);
                startActivity(intent);
            }
        });
        //set su kien view
        //view nguoi dung
        sharedPreferences = getSharedPreferences("ten_nguoi_dung", MODE_PRIVATE);
        String tennguoidung = sharedPreferences.getString("ten_nguoi_dung", "");
        if (!tennguoidung.isEmpty()) {
            ten_nguoi_dungC.setText("Xin chào, " + tennguoidung);
            navigationView.getMenu().findItem(R.id.nav_4).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_5).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_6).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_7).setVisible(false);
            layout_2C.setVisibility(View.GONE);
        } else {
            // view admin
            sharedPreferences_admin = getSharedPreferences("ten_nguoi_dung_admin", MODE_PRIVATE);
            String tennguoidung_admin = sharedPreferences_admin.getString("ten_nguoi_dung_admin", "");
            if (!tennguoidung_admin.isEmpty()) {
                ten_nguoi_dungC.setText("Xin chào, " + tennguoidung_admin);
                layout_2C.setVisibility(View.GONE);
            } else {
                // view nguoi dung chua dang nhap
                ten_nguoi_dungC.setText("");
                btnDangNhapC.setVisibility(View.VISIBLE);
                btnDangKyC.setVisibility(View.VISIBLE);
                navigationView.getMenu().findItem(R.id.nav_4).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_5).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_6).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_7).setVisible(false);
            }
        }

    }

    // đếm stack để hiện thông báo thoát
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount == 0) {
            if (isTaskRoot()) {
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận thoát ứng dụng")
                        .setMessage("Bạn có muốn thoát ứng dụng không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Thoát khỏi ứng dụng
                                DrawerBaseActivity.super.onBackPressed();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        // sử dụng được nhiều phiên bản từ 8.0 trở lên
        int itemId = item.getItemId();
        if (itemId == R.id.nav_1) {
            startActivity(new Intent(this, DienThoai.class));
            overridePendingTransition(0,0);
        } else if (itemId == R.id.nav_2) {
            startActivity(new Intent(this, Laptop.class));
            overridePendingTransition(0,0);
        }else if (itemId == R.id.nav_4) {
            startActivity(new Intent(this, QuanLySanPham.class));
            overridePendingTransition(0,0);
        }else if (itemId == R.id.nav_5) {
            startActivity(new Intent(this, DanhSachTaiKhoan.class));
            overridePendingTransition(0,0);
        }else if (itemId == R.id.nav_6) {
            startActivity(new Intent(this, DanhSachDatHang.class));
            overridePendingTransition(0,0);
        }else if (itemId == R.id.nav_7) {
            startActivity(new Intent(this, DanhSachXacNhan.class));
            overridePendingTransition(0,0);
        }else if (itemId == R.id.nav_8) {
            startActivity(new Intent(this, Loa.class));
            overridePendingTransition(0,0);
        }
        return false;
    }
    //option menu ( Them Cho nay ) #####
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_giohang){
            startActivity(new Intent(this, GioHang.class));
            overridePendingTransition(0,0);
        } else if (itemId == R.id.nav_nguoidung) {
            startActivity(new Intent(this, NguoiDung.class));
            overridePendingTransition(0,0);
        }
        return super.onOptionsItemSelected(item);
    }
    protected void allocateActivityTitle(String titleString){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleString);
        }
    }
}