package com.zurefaseverler.kithub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice,txtProductQuantity;
    public ImageView imgProductPhoto;
    private ItemClickListener itemClickListener;
    private ElegantNumberButton urunsayisi;

    public CartViewHolder(View itemView) {
        super(itemView);
        txtProductName =itemView.findViewById(R.id.cart_product_name);
        txtProductPrice =itemView.findViewById(R.id.cart_product_price);
        urunsayisi =itemView.findViewById(R.id.product_quantity1);
        imgProductPhoto =itemView.findViewById(R.id.cart_product_photo);
    }


    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);



    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }



}
