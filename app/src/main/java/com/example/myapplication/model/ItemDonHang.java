package com.example.myapplication.model;

public class ItemDonHang {
    int madonhang;
    String ten;
    int gia;
    int soLuong;
    String mauSac;
    byte [] anh ;
    int trangThai;

    String taiKhoang;
    int kichCo;

    public ItemDonHang(int madonhang, String ten, int gia, int soLuong, String mauSac, byte[] anh, int trangThai, String taiKhoang, int kichCo) {
        this.madonhang = madonhang;
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
        this.mauSac = mauSac;
        this.anh = anh;
        this.trangThai = trangThai;
        this.taiKhoang = taiKhoang;
        this.kichCo = kichCo;
    }

    public ItemDonHang(String ten, int gia, int soLuong, String mauSac, byte[] anh, int trangThai, String taiKhoang, int kichCo) {
        this.ten = ten;
        this.gia = gia;
        this.soLuong = soLuong;
        this.mauSac = mauSac;
        this.anh = anh;
        this.trangThai = trangThai;
        this.taiKhoang = taiKhoang;
        this.kichCo = kichCo;
    }
    public int getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(int madonhang) {
        this.madonhang = madonhang;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTaiKhoang() {
        return taiKhoang;
    }

    public void setTaiKhoang(String taiKhoang) {
        this.taiKhoang = taiKhoang;
    }

    public int getKichCo() {
        return kichCo;
    }

    public void setKichCo(int kichCo) {
        this.kichCo = kichCo;
    }
}
