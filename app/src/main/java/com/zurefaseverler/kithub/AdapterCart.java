package com.zurefaseverler.kithub;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
interface OnAdapterItemClickListener {
    void onItemClicked();
}

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.designCard> {

    private Context context;
    private ArrayList<Cart> list;


    public AdapterCart(@NonNull Context context, ArrayList<Cart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    public designCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_items_layout,parent,false);
        return new designCard(view);
    }

    @Override

    public void onBindViewHolder(@NonNull final designCard holder, final int position) {
        holder.kitapadi.setText(list.get(position).getPname());
        holder.fiyat.setText((list.get(position).getPrice() + " ₺"));
        holder.adet.setNumber(list.get(position).getQuantity());

        String[] temp = list.get(position).getImage().split("html/");
        if(temp.length == 1){
            Picasso.get().load(list.get(position).getImage()).into(holder.img);
        }
        else{
            Picasso.get().load("http://18.204.251.116/" + temp[1]).into(holder.img);
        }

        holder.update_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id", -1));
                changeItemQuantity(customer_id, holder.adet.getNumber(), list.get(position).getPid());
            }
        });

        holder.adet.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                float newTotal = newValue * list.get(position).getPrice();
                list.get(position).setTotalPrice(newTotal);
                int total = 0;
                for(int i=0; i<list.size(); i++){
                    total += list.get(i).getTotalPrice();
                }
                String total_ = total + " ₺";
                CartActivity.totalPrice.setText(total_);
            }
        });
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
        Button update_quantity;

        public designCard(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_product_photo);
            kitapadi =itemView.findViewById(R.id.cart_product_name);
            fiyat=itemView.findViewById(R.id.cart_product_price);
            adet=itemView.findViewById(R.id.cartItem_product_quantity1);
            update_quantity = itemView.findViewById(R.id.cartItem_updateQuantity);
        }

    }
    public void changeItemQuantity(final String customer_id, final String number, final String book_id){
        String url = "http://18.204.251.116/update_cart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("operation", "change_quantity");
                params.put("customer_id", customer_id);
                params.put("p_id", book_id);
                params.put("quantity", number);
                return params;
            }
        };
        NetworkRequests.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
