package com.example.ban_dt_dapm.Xu_Ly_Giao_Dien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.ban_dt_dapm.Cac_Loai_San_Pham.DienThoai;
import com.example.ban_dt_dapm.Cac_Loai_San_Pham.Laptop;
import com.example.ban_dt_dapm.Nguoi_Dung.DangKy;
import com.example.ban_dt_dapm.Nguoi_Dung.DangNhap;
import com.example.ban_dt_dapm.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Button btnDangNhapC, btnDangKyC;
    NavigationView navigationViewC;

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
        //anh xa button va view navigation
        navigationViewC = findViewById(R.id.nav_View);
        View headerView = navigationViewC.getHeaderView(0);
        btnDangNhapC = headerView.findViewById(R.id.button_dangNhap_0);
        btnDangKyC = headerView.findViewById(R.id.button_dangKy_0);
        // set su kien button
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // đếm stack để hiện thông báo thoát app
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
        }
        return false;
    }
    protected void allocateActivityTitle(String titleString){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleString);
        }
    }
}