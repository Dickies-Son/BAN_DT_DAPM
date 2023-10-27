package com.example.ban_dt_sp.Xu_Ly_Danh_Sach;

public class get_set_nguoi_dung {
    String ten;
    String so_dien_thoai;
    String mat_khau;

    public get_set_nguoi_dung(String ten, String so_dien_thoai, String mat_khau) {
        this.ten = ten;
        this.so_dien_thoai = so_dien_thoai;
        this.mat_khau = mat_khau;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }
}
