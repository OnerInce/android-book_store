package com.zurefaseverler.kithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.designCard> {

    @NonNull
    public int  totalPrice=0;
    Context context;
    List <Cart> list;


    public AdapterCart(@NonNull Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
    }

    public designCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_items_layout,parent,false);
        return new designCard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull designCard holder, final int position) {


        holder.img.setImageResource(R.drawable.cart_huzursuzluk);
        holder.kitapadi.setText(list.get(position).getPname());
        holder.fiyat.setText( new StringBuilder(list.get(position).getPrice()).append(" TL"));
        holder.adet.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart= list.get(position);
                cart.setQuantity(String.valueOf(newValue));
            }
        });

        int oneTypeProductPrice = ((Integer.parseInt(list.get(position).getPrice())) * Integer.parseInt(list.get(position).getQuantity()));

        totalPrice = totalPrice + oneTypeProductPrice;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class designCard extends RecyclerView.ViewHolder{

        ImageView img;
        TextView kitapadi;
        TextView fiyat;
        ElegantNumberButton adet;


        public designCard(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_product_photo);
            kitapadi =itemView.findViewById(R.id.cart_product_name);
            fiyat=itemView.findViewById(R.id.cart_product_price);
            adet=itemView.findViewById(R.id.product_quantity1);

        }
    }

}
