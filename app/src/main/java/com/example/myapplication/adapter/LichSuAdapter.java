package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.model.ItemDonHang;
import com.example.myapplication.model.ItemGioHang;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ItemDonHang> list;
    private GiayDAO giayDAO;

    public LichSuAdapter(Context context, ArrayList<ItemDonHang> list, GiayDAO giayDAO) {
        this.context = context;
        this.list = list;
        this.giayDAO = giayDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_lich_su, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tengiay.setText("Tên giày: "+ list.get(position).getTen());
        holder.gia.setText(String.valueOf(list.get(position).getGia()));
        holder.kichCo.setText("Kích cỡ: "+list.get(position).getKichCo());
        holder.mau.setText(list.get(position).getMauSac());
        holder.soLuong.setText("Số Lượng: "+list.get(position).getSoLuong());

        //        chuyen byte[] thanh bitmap
        byte [] hinh =  list.get(position).getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.anh.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tengiay,gia,soLuong,kichCo,mau;
        ImageView anh;
        Button xoaGioHang,xacNhan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tengiay = itemView.findViewById(R.id.tengiay);
            gia = itemView.findViewById(R.id.gia);
            anh = itemView.findViewById(R.id.anh);
            soLuong = itemView.findViewById(R.id.soLuong);
            kichCo = itemView.findViewById(R.id.kichCo);
            mau = itemView.findViewById(R.id.mau);
            xoaGioHang = itemView.findViewById(R.id.xoaGioHang);
            xacNhan = itemView.findViewById(R.id.xacNhan);
        }
    }
    private void getDS(){
        list.clear();
        SharedPreferences sharedPreferences =  context.getSharedPreferences("THONGTIN", context.MODE_PRIVATE);
        String tk = sharedPreferences.getString("taikhoan", null);
        list = giayDAO.layItemDonHang();
        notifyDataSetChanged();
    }
}
