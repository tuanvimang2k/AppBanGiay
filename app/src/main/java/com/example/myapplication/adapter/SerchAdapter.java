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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.Deltalitem;
import com.example.myapplication.activity.GioHangActivity;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.SerchDao;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SerchAdapter extends RecyclerView.Adapter<SerchAdapter.ViewHolder> implements Filterable {

        private Context context;
        private List<Product> list;
        private List<Product> list1;



    public SerchAdapter(Context context, ArrayList<Product> list, SerchDao serchDao) {
        this.context = context;
        this.list = list;
        this.list1 = list;
    }


    @NonNull
        @Override
        public SerchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.item_serch, parent, false);

            return new SerchAdapter.ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product  = list.get(position);
        if(product == null){
            return;
        }


        holder.txtname.setText(list.get(position).getTengiay());
        holder.txtgia.setText(String.valueOf(list.get(position).getGia()));
        byte [] hinh =  list.get(position).getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.imganh.setImageBitmap(bitmap);
        holder.btnbamvone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemdelta(product);
            }
        });

    }
    public void itemdelta(Product product){
        Intent intent  = new Intent(context, Deltalitem.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("truyenne",product);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

        @Override
        public int getItemCount() {
        if(list !=null){
            return  list.size();
        }
            return 0;
        }



    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView txtgia, txtname;
         ImageView imganh;
         RelativeLayout btnbamvone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.imgserch);
            txtgia = itemView.findViewById(R.id.txtgia);
            txtname = itemView.findViewById(R.id.txtten);
            btnbamvone = itemView.findViewById(R.id.bamvone);


        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
         String strSerch = constraint.toString();
         if(strSerch.isEmpty()){
             list = null;
         }else {
             List<Product> list2 = new ArrayList<>();
             for (Product product: list1){
                 if(product.getTengiay().toLowerCase().contains(strSerch.toLowerCase())){
                     list2.add(product);
                 }
             }
           list = list2;
         }
         FilterResults filterResults = new FilterResults();
         filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                 list = (List<Product>) results.values;
                 notifyDataSetChanged();
            }
        };
    }

}
