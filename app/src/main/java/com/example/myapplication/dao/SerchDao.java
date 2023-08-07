package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.Dbhelper;
import com.example.myapplication.model.ItemGioHang;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class SerchDao {
    Dbhelper dbhelper;

    public SerchDao(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<Product> getDS() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GIAY", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add( new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getBlob(5),
                        cursor.getInt(6)
                ));
            }while (cursor.moveToNext());
        }
        return  list;
    }

///1


}
