package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.Dbhelper;
import com.example.myapplication.model.ItemDonHang;
import com.example.myapplication.model.ItemGioHang;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class GiayDAO {
    Dbhelper dbhelper;

    public GiayDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<Product> getDSPRO() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GIAY", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getBlob(5),
                        cursor.getInt(6)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // magiay integer primary key autoincrement , tengiay text, giagiay integer, soluong integer, mausac text, kichco integer, anh blob, maloaigiay integer
    public boolean themGiay(Product product) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tengiay", product.getTengiay());
        contentValues.put("giagiay", product.getGia());
        contentValues.put("mausac", product.getMausac());
        contentValues.put("kichco", product.getKichco());
        contentValues.put("anh", product.getAnh());
        contentValues.put("maloaigiay", product.getMaloaigiay());
        long check = sqLiteDatabase.insert("GIAY", null, contentValues);
        return check > 0;
    }

    public boolean suaGiay(Product product) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tengiay", product.getTengiay());
        contentValues.put("giagiay", product.getGia());
        contentValues.put("mausac", product.getMausac());
        contentValues.put("kichco", product.getKichco());
        contentValues.put("anh", product.getAnh());
        contentValues.put("maloaigiay", product.getMaloaigiay());
        long check = sqLiteDatabase.update("GIAY", contentValues, "magiay = ?", new String[]{String.valueOf(product.getMagiay())});
        return check > 0;
    }

    public boolean xoaGiay(int maGiay) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("Giay", "magiay=?", new String[]{String.valueOf(maGiay)});
        return check > 0;
    }

    public ArrayList<Product> getDSPROLoai(int maloaigiay) {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GIAY WHERE maloaigiay =?", new String[]{String.valueOf(maloaigiay)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getBlob(5),
                        cursor.getInt(6)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themVaoGH(int magiay, int soluong, String tk) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("magiay", magiay);
        contentValues.put("soluong", soluong);
        contentValues.put("taikhoan", tk);
        contentValues.put("trangthai", 0);
        long check = sqLiteDatabase.insert("GIOHANG", null, contentValues);
        return check > 0;
    }

    public ArrayList<ItemGioHang> layItemGioHang(String tk) {

        ArrayList<ItemGioHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT GIAY.tengiay,GIAY.giagiay,GIOHANG.soluong,GIAY.mausac,GIAY.anh, GIOHANG.magiohang " +
                "FROM GIAY " +
                "INNER JOIN GIOHANG " +
                "ON GIAY.magiay = GIOHANG.magiay " +
                "WHERE GIOHANG.taikhoan= ? ", new String[]{String.valueOf(tk)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ItemGioHang(cursor.getInt(5), cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }

    //    taikhoan references NGUOIDUNG(taikhoan),magiay integer references GIAY(magiay), soluong integer
    public boolean themVaoDH(int trangthai, String tk, int magiay, int soluong) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai", trangthai);
        contentValues.put("taikhoan", tk);
        contentValues.put("magiay", magiay);
        contentValues.put("soluong", soluong);
        long check = sqLiteDatabase.insert("DONHANG", null, contentValues);
        return check > 0;
    }

    public ArrayList<ItemDonHang> layItemDonHang() {

        ArrayList<ItemDonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  GIAY.tengiay,GIAY.giagiay,DONHANG.soluong,GIAY.mausac,GIAY.anh," +
                "DONHANG.trangthai,DONHANG.taikhoan,GIAY.kichco ,DONHANG.madon " +
                "FROM GIAY " +
                "INNER JOIN DONHANG " +
                "ON GIAY.magiay = DONHANG.magiay " +
                "WHERE   DONHANG.trangthai= 0", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ItemDonHang(cursor.getInt(8), cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean chuyentrangtrangthai(int madonhang) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai", 1);
        long check = sqLiteDatabase.update("DONHANG", contentValues, "madon = ?", new String[]{String.valueOf(madonhang)});
        return check > 0;
    }

    public boolean xoaItemGH(int magiohang) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GIOHANG", "magiohang=?", new String[]{String.valueOf(magiohang)});
        return check > 0;
    }

    //    1 là tick
    public boolean doiTrangThaiGH1(int magiohang) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai", 1);
        long check = sqLiteDatabase.update("GIOHANG", contentValues, "magiohang = ?", new String[]{String.valueOf(magiohang)});
        return check > 0;
    }

    //    0 là không tick
    public boolean doiTrangThaiGH0(int magiohang) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai", 0);
        long check = sqLiteDatabase.update("GIOHANG", contentValues, "magiohang = ?", new String[]{String.valueOf(magiohang)});
        return check > 0;
    }

    public ArrayList<ItemDonHang> LichSuDonHang(String tk) {

        ArrayList<ItemDonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  GIAY.tengiay,GIAY.giagiay,DONHANG.soluong,GIAY.mausac,GIAY.anh," +
                "DONHANG.trangthai,DONHANG.taikhoan,GIAY.kichco ,DONHANG.madon " +
                "FROM GIAY " +
                "INNER JOIN DONHANG " +
                "ON GIAY.magiay = DONHANG.magiay " +
                "WHERE DONHANG.taikhoan= ? AND DONHANG.trangthai= 1", new String[]{String.valueOf(tk)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ItemDonHang(cursor.getInt(8), cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<ItemDonHang> layItemLichSuDH() {

        ArrayList<ItemDonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  GIAY.tengiay,GIAY.giagiay,DONHANG.soluong,GIAY.mausac,GIAY.anh," +
                "DONHANG.trangthai,DONHANG.taikhoan,GIAY.kichco ,DONHANG.madon " +
                "FROM GIAY " +
                "INNER JOIN DONHANG " +
                "ON GIAY.magiay = DONHANG.magiay " +
                "WHERE   DONHANG.trangthai= 1", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ItemDonHang(cursor.getInt(8), cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<ItemGioHang> layItemGHDaXacNhan(String tk) {

        ArrayList<ItemGioHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
//int magiohang, String ten, int gia, int soLuong, String mauSac, byte[] anh, int magiay
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT GIOHANG.magiohang,  GIAY.tengiay,GIAY.giagiay,GIOHANG.soluong,GIAY.mausac,GIAY.anh,GIOHANG.magiay " +
                "FROM GIAY " +
                "INNER JOIN GIOHANG " +
                "ON GIAY.magiay = GIOHANG.magiay " +
                "WHERE GIOHANG.taikhoan= ? AND GIOHANG.trangthai = 1 ", new String[]{String.valueOf(tk)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ItemGioHang(cursor.getInt(0), cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getBlob(5),
                        cursor.getInt(6)
                ));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public boolean xoaItemGHDXN() {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GIOHANG", "trangthai = 1", null);
        return check > 0;
    }
}
