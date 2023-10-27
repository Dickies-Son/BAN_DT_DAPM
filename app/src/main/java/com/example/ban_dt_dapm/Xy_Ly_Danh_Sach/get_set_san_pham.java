package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

public class get_set_san_pham {
    String tenSP;
    String moTa;
    String giaTien;
    String hinhAnh;

    public get_set_san_pham(String tenSP, String moTa, String giaTien, String hinhAnh) {
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }
}
