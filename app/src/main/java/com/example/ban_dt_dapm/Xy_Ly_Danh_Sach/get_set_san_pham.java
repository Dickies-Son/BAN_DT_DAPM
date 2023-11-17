package com.example.ban_dt_dapm.Xy_Ly_Danh_Sach;

public class get_set_san_pham {
    String tenSP;
    String moTa;
    String giaTien;
    String loaiSP;
    String hinhAnh;
    String soLuong;
    String idSP;

    public get_set_san_pham(String tenSP, String moTa, String giaTien, String hinhAnh,String soLuong,String loaiSP,String idSP) {
        this.loaiSP = loaiSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.idSP = idSP;
    }

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
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
