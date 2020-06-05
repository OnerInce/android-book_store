package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull setDetail holder, int position) {

        holder.orderDetailUserPrice.setText(orderDetailUserList.get(position).getOrderDetailPrice() + " ₺");
        holder.orderDetailUserName.setText(orderDetailUserList.get(position).getOrderDetailName());
        holder.orderDetailItemQuantity.setText("x" + orderDetailUserList.get(position).getOrderDetailQuantity());

        String[] temp = orderDetailUserList.get(position).getOrderDetailImage().split("html/");
        if(temp.length == 1){
            Picasso.get().load(temp[0]).into(holder.orderDetailUserPhoto);
        }
        else{
            Picasso.get().load("http://18.204.251.116/" + temp[1]).into(holder.orderDetailUserPhoto);
        }


    }

    @Override
    public int getItemCount() {
        return orderDetailUserList.size();
    }

    public class setDetail extends RecyclerView.ViewHolder{

        ImageView orderDetailUserPhoto;
        TextView orderDetailUserName;
        TextView orderDetailUserPrice;
        TextView orderDetailItemQuantity;

        public setDetail(@NonNull View itemView) {
            super(itemView);
            orderDetailUserName = itemView.findViewById(R.id.order_detail_item_name);
            orderDetailUserPhoto = itemView.findViewById(R.id.order_detail_item_photo);
            orderDetailUserPrice = itemView.findViewById(R.id.order_detail_item_price);
            orderDetailItemQuantity = itemView.findViewById(R.id.order_detail_item_quantity);

        }
    }
}
