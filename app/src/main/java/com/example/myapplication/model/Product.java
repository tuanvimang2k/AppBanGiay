package com.example.myapplication.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int magiay;
    private String tengiay;
    private int gia;
    private String mausac;
    private int kichco;
    private byte [] anh;
    private int maloaigiay;

    public Product(int magiay, String tengiay, int gia, String mausac, int kichco, byte[] anh, int maloaigiay) {
        this.magiay = magiay;
        this.tengiay = tengiay;
        this.gia = gia;
        this.mausac = mausac;
        this.kichco = kichco;
        this.anh = anh;
        this.maloaigiay = maloaigiay;
    }

    public Product(String tengiay, int gia, String mausac, int kichco, byte[] anh, int maloaigiay) {
        this.tengiay = tengiay;
        this.gia = gia;
        this.mausac = mausac;
        this.kichco = kichco;
        this.anh = anh;
        this.maloaigiay = maloaigiay;
    }

    public int getMagiay() {
        return magiay;
    }

    public void setMagiay(int magiay) {
        this.magiay = magiay;
    }

    public String getTengiay() {
        return tengiay;
    }

    public void setTengiay(String tengiay) {
        this.tengiay = tengiay;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public int getKichco() {
        return kichco;
    }

    public void setKichco(int kichco) {
        this.kichco = kichco;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public int getMaloaigiay() {
        return maloaigiay;
    }

    public void setMaloaigiay(int maloaigiay) {
        this.maloaigiay = maloaigiay;
    }
}