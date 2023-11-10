package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

public class get_set_nguoi_dung {
    String ten;
    String so_dien_thoai;
    String mat_khau;
    String dia_chi;
    String gio_hang;

    public get_set_nguoi_dung(String ten, String so_dien_thoai, String mat_khau,String dia_chi,String gio_hang) {
        this.ten = ten;
        this.so_dien_thoai = so_dien_thoai;
        this.mat_khau = mat_khau;
        this.dia_chi = dia_chi;
        this.gio_hang = gio_hang;
    }

    public String getGio_hang() {
        return gio_hang;
    }

    public void setGio_hang(String gio_hang) {
        this.gio_hang = gio_hang;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
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

