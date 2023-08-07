package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context context) {
        super(context, "FIVESOLE", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbLoaiGiay = "CREATE TABLE LOAIGIAY(maloaigiay integer primary key autoincrement , tenloai text)";
        db.execSQL(dbLoaiGiay);

        String dbGiay = "CREATE TABLE GIAY (magiay integer primary key autoincrement , tengiay text, giagiay integer, mausac text, kichco integer, anh blob, maloaigiay integer references LOAIGIAY(maloaigiay))";
        db.execSQL(dbGiay);

        String dbNguoiDung = "CREATE TABLE NGUOIDUNG (taikhoan text primary key, matkhau text, phanquyen integer)";
        db.execSQL(dbNguoiDung);

        String Donhang = "CREATE TABLE DONHANG (madon integer primary key autoincrement, trangthai integer, taikhoan references NGUOIDUNG(taikhoan),magiay integer references GIAY(magiay), soluong integer )";
        db.execSQL(Donhang);

        String dbdChitietdonhang = "CREATE TABLE CTDH (machitiet_donhang integer primary key autoincrement , magiay integer references GIAY(magiay) , madon integer references DONHANG(madon))";
        db.execSQL(dbdChitietdonhang);

        String dbGioHang = "CREATE TABLE GIOHANG(magiohang integer primary key autoincrement, magiay integer references GIAY(magiay),soluong integer ,trangthai integer,taikhoan text references NGUOIDUNG(taikhoan))";
        db.execSQL(dbGioHang);


        db.execSQL( "INSERT INTO LOAIGIAY VALUES (1,'giày thể thao'), (2, 'giày lười'),(3, 'giày leo núi')");
//        db.execSQL("INSERT INTO GIAY VALUES(1,'thuong dinh',2000,5,'den',42,65498312,1),(2,'bitis',2000,5,'trang',43,7845312,1)");
        db.execSQL("INSERT INTO NGUOIDUNG VALUES ('dungdo','123',0),('duysui','123',1)");
//        db.execSQL("INSERT INTO DONHANG VALUES (1,2000,'dungdo'), (2,300, 'dungdo')");
//        db.execSQL("INSERT INTO CTDH VALUES (1,1,1),(2,2,1)");
//        db.execSQL("INSERT INTO GIOHANG VALUES(1,1,'dungdo',2)");





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS LOAIGIAY");
            db.execSQL("DROP TABLE IF EXISTS GIAY");
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS DONHANG");
            db.execSQL("DROP TABLE IF EXISTS CTDH");
            db.execSQL("DROP TABLE IF EXISTS GIOHANG");
            onCreate(db);
        }
    }
}