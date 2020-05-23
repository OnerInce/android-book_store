package com.zurefaseverler.kithub;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Map;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.designCard> {

    private int  totalPrice=0;
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
    public void onBindViewHolder(@NonNull final designCard holder, int position) {
        holder.kitapadi.setText(list.get(position).getPname());
        holder.fiyat.setText( new StringBuilder(list.get(position).getPrice()).append(" TL"));
        holder.adet.setNumber(list.get(position).getQuantity());

        String[] temp = list.get(position).getImage().split("html/");
        Picasso.get().load("http://18.204.251.116/"+temp[1]).fit().into(holder.img);

       holder.update_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id", -1));
                changeItemQuantity(customer_id, holder.adet.getNumber());
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
    public void changeItemQuantity(final String customer_id, final String number){
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
                params.put("quantity", number);
                return params;
            }
        };
        NetworkRequests.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
