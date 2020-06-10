package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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
import java.util.Locale;
import java.util.Map;

import static com.zurefaseverler.kithub.StartUp.HOST;

interface OnAdapterItemClickListener {
    void onItemClicked();
}

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.designCard> {

    private Context context;
    private ArrayList<Cart> list;


    AdapterCart(@NonNull Context context, ArrayList<Cart> list) {
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
        String s = String.format(Locale.ITALY, "%.2f", list.get(position).getPrice());

        holder.kitapadi.setText(list.get(position).getPname());
        holder.fiyat.setText((s + " ₺"));
        holder.adet.setNumber(list.get(position).getQuantity());

        String[] temp = list.get(position).getImage().split("html/");
        if(temp.length == 1){
            Picasso.get().load(list.get(position).getImage()).into(holder.img);
        }
        else{
            Picasso.get().load(HOST + temp[1]).into(holder.img);
        }

        holder.adet.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id", -1));

                String newQuantity = holder.adet.getNumber();
                list.get(position).setQuantity(newQuantity);
                changeItemQuantity(customer_id, newQuantity, list.get(position).getPid());


                float newTotal = newValue * list.get(position).getPrice();
                list.get(position).setTotalPrice(newTotal);
                CartActivity.total = 0;
                for(int i = 0; i < list.size(); i++)
                    CartActivity.total += list.get(i).getTotalPrice();
                String s = String.format(Locale.ITALY, "%.2f", CartActivity.total);
                CartActivity.totalPrice.setText(s + " ₺");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class designCard extends RecyclerView.ViewHolder{

        ImageView img;
        TextView kitapadi;
        TextView fiyat;
        ElegantNumberButton adet;

        designCard(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_product_photo);
            kitapadi = itemView.findViewById(R.id.cart_product_name);
            fiyat = itemView.findViewById(R.id.cart_product_price);
            adet = itemView.findViewById(R.id.cartItem_product_quantity1);
        }

    }
    private void changeItemQuantity(final String customer_id, final String number, final String book_id){
        String url = HOST + "update_cart.php";
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
