package com.zurefaseverler.kithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.setObject> {

    @NonNull

    Context context;

    ArrayList<OrderHistoryObject> orderHistoryList;


    public OrderHistoryAdapter(@NonNull Context context, ArrayList<OrderHistoryObject> orderHistoryList) {
        this.context = context;
        this.orderHistoryList = orderHistoryList;
    }
    @Override
    @NonNull
    public setObject onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item,parent,false);
        return new setObject(view);
    }

    @Override
    public void onBindViewHolder(@NonNull setObject holder, int position) {
        holder.date.setText(orderHistoryList.get(position).getProductDate());
        holder.name.setText(orderHistoryList.get(position).getProductName());
        holder.price.setText(orderHistoryList.get(position).getProductPrice());
        holder.img.setImageResource(R.drawable.cart_huzursuzluk);

    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class setObject extends RecyclerView.ViewHolder{

        ImageView img;
        TextView date,name,price;



        public setObject(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.order_photo);
            date=itemView.findViewById(R.id.order_date);
            name=itemView.findViewById(R.id.order_name);
            price=itemView.findViewById(R.id.order_price);
        }
    }
}
