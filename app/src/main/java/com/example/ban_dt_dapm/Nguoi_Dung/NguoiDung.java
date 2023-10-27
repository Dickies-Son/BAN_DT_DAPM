package com.example.ban_dt_dapm.Nguoi_Dung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ban_dt_dapm.R;
import com.example.ban_dt_dapm.Xu_Ly_Giao_Dien.DrawerBaseActivity;
import com.example.ban_dt_dapm.databinding.ActivityNguoiDungBinding;

public class NguoiDung extends DrawerBaseActivity {
    ActivityNguoiDungBinding activityNguoiDungBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNguoiDungBinding = ActivityNguoiDungBinding.inflate(getLayoutInflater());
        setContentView(activityNguoiDungBinding.getRoot());
        allocateActivityTitle("Cá nhân");
    }
}