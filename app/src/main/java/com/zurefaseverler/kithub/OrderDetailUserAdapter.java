package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailUserAdapter extends RecyclerView.Adapter<OrderDetailUserAdapter.setDetail> {

    private Context context;
    private ArrayList<OrderDetailUserObject> orderDetailUserList;

    public OrderDetailUserAdapter(Context context, ArrayList<OrderDetailUserObject> orderDetailUserList) {
        this.context = context;
        this.orderDetailUserList = orderDetailUserList;
    }

    @NonNull
    @Override
    public setDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_detail_item,parent,false);
        return new setDetail(view);
    }

    @Override
    public void onBindViewHolder(@NonNull setDetail holder, int position) {

        holder.orderDetailUserPrice.setText(orderDetailUserList.get(position).getOrderDetailPrice());
        holder.orderDetailUserPhoto.setImageResource(orderDetailUserList.get(position).getOrderDetailImage());
        holder.orderDetailUserName.setText(orderDetailUserList.get(position).getOrderDetailName());

    }

    @Override
    public int getItemCount() {
        return orderDetailUserList.size();
    }

    public class setDetail extends RecyclerView.ViewHolder{

        ImageView orderDetailUserPhoto;
        TextView orderDetailUserName;
        TextView orderDetailUserPrice;

        public setDetail(@NonNull View itemView) {
            super(itemView);
            orderDetailUserName = itemView.findViewById(R.id.order_detail_item_name);
            orderDetailUserPhoto = itemView.findViewById(R.id.order_detail_item_photo);
            orderDetailUserPrice = itemView.findViewById(R.id.order_detail_item_price);



        }
    }
}
