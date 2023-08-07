package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.Deltalitem;
import com.example.myapplication.activity.GioHangActivity;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class GiayUserAdapter extends RecyclerView.Adapter<GiayUserAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Product> list;
    private GiayDAO giayDAO;

    public GiayUserAdapter(Context context, ArrayList<Product> list, GiayDAO giayDAO) {
        this.context = context;
        this.list = list;
        this.giayDAO = giayDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_giay_new, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product  = list.get(position);
        if(product == null){
            return;
        }
        holder.tengiay.setText(list.get(position).getTengiay());
        holder.gia.setText(String.valueOf(list.get(position).getGia()));
        //        chuyen byte[] thanh bitmap
        byte [] hinh =  list.get(position).getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.anh.setImageBitmap(bitmap);
        holder.txtshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item(product);
            }
        });

        holder.btnthemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiayDAO dao = new GiayDAO(v.getContext());
                SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
                String tk = sharedPreferences.getString("taikhoan",null);

                if(dao.themVaoGH(list.get(position).getMagiay(),1,String.valueOf(tk))){
                    int ma = list.get(position).getMagiay();
                    Toast.makeText(v.getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, GioHangActivity.class));
                }else {
                    Toast.makeText(v.getContext(), "thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
     public  void item(Product product){
         Intent intent  = new Intent(context, Deltalitem.class);
         Bundle bundle = new Bundle();
         bundle.putSerializable("truyenne",product);
         intent.putExtras(bundle);
         context.startActivity(intent);
     }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView tengiay,gia;
        ImageView anh;
        LinearLayout btnthemGH;
        LinearLayout txtshow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tengiay = itemView.findViewById(R.id.tengiay);
            gia = itemView.findViewById(R.id.gia);
            anh = itemView.findViewById(R.id.anh);
            btnthemGH = itemView.findViewById(R.id.btnthemGH);
            txtshow = itemView.findViewById(R.id.txtshow);
        }
    }
}
