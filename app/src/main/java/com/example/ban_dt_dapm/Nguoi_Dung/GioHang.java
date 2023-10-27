package com.example.ban_dt_dapm.Nguoi_Dung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.databinding.ActivityGioHangBinding;

public class GioHang extends DrawerBaseActivity {
    ActivityGioHangBinding activityGioHangBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGioHangBinding = ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(activityGioHangBinding.getRoot());
        allocateActivityTitle("Giỏ hàng của bạn");
    }
}