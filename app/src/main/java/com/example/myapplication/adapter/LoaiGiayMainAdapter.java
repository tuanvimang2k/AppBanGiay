package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainAdmin;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.LoaiGiayDAO;
import com.example.myapplication.model.LoaiGiay;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class LoaiGiayMainAdapter extends RecyclerView.Adapter<LoaiGiayMainAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiGiay> list;
    private LoaiGiayDAO dao;
    private RecyclerView recyclerView;

    public LoaiGiayMainAdapter(Context context, ArrayList<LoaiGiay> list, LoaiGiayDAO dao, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.dao = dao;
        this.recyclerView = recyclerView;
    }

    //
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaigiaymain, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenloai.setText(list.get(position).getTenloai());

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                GridLayoutManager linearLayoutManager = new GridLayoutManager(context,2);
                recyclerView.setLayoutManager(linearLayoutManager);
                ArrayList<Product> list1  = new ArrayList<>();
                GiayDAO dao1 = new GiayDAO(context);
                Giayadapter giayadapter ;
                list1 = dao1.getDSPROLoai(list.get(position).getMaloai());
                giayadapter = new Giayadapter(context, list1, dao1);
                recyclerView.setAdapter(giayadapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenloai;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenloai = itemView.findViewById(R.id.txtTenloai);
            linear = itemView.findViewById(R.id.linear);
        }
    }
}
