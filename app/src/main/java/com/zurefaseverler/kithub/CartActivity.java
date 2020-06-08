package com.zurefaseverler.kithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;


    public static TextView totalPrice;
    private Button updateQuantity;
    private ImageButton back;
    private String customer_id, s;
    static float total;

    ArrayList<Cart> cartList;
    AdapterCart adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        customer_id = Integer.toString(sharedPref.getInt("id", -1));
        totalPrice = findViewById(R.id.totalPrice);

        recyclerView = findViewById(R.id.cart_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        setCartContent();

        NextProcessBtn = findViewById(R.id.next_process_button);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartList.size() == 0)
                    Toast.makeText(getApplicationContext(), R.string.cart_no_book, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CartActivity.this, Payment.class);
                for (int i = 0; i < cartList.size(); i++){

                    Cart currentObj = cartList.get(i);
                    String cartItemID = "ITEM" + i;
                    String book_id = currentObj.getPid();
                    String quantity = currentObj.getQuantity();
                    String book_price = Float.toString(currentObj.getPrice());

                    intent.putExtra(cartItemID + " BOOK", book_id);
                    intent.putExtra(cartItemID + " QUANTITY", quantity);
                    intent.putExtra(cartItemID + " PRICE", book_price);
                    intent.putExtra(cartItemID + " CUSTOMER", customer_id);
                }
                intent.putExtra("TOTAL_ORDER_PRICE", total);
                startActivity(intent);
            }
        });

    }

    public void setCartContent() {
        getCurrentCustomerCart(new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                setNewTotalPrice();
                findViewById(R.id.cartActivity_progressBar).setVisibility(View.GONE);
                adapter = new AdapterCart(CartActivity.this, cartList);
                recyclerView.setAdapter(adapter);
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void setNewTotalPrice() {
        total = 0;
        for(int i = 0; i < cartList.size(); i++)
            total += cartList.get(i).getTotalPrice();
        s = String.format(Locale.ITALY, "%.2f", total);
        totalPrice.setText(s + " ₺");
    }


    private void getCurrentCustomerCart(final VolleyResponseListener listener) {
        String url = "http://18.204.251.116/get_customer_cart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            cartList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String price = jsonObject.getString("price");

                                float discountFloat = 0;
                                try{
                                    discountFloat = Float.parseFloat(jsonObject.getString("discount"));
                                    price = Float.toString(Integer.parseInt(price) - (Integer.parseInt(price) * discountFloat) / 100);
                                } catch (NumberFormatException ex) {
                                    discountFloat = (float) 0.0;
                                }

                                String p_id = jsonObject.getString("p_id");
                                String title = jsonObject.getString("title");
                                String quantity = jsonObject.getString("quantity");
                                String image = jsonObject.getString("image");

                                Cart cartItem = new Cart(p_id, title, Float.parseFloat(price), quantity, discountFloat, image,
                                            Float.parseFloat(price) * Integer.parseInt(quantity));
                                cartList.add(cartItem);
                            }
                            listener.onResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                params.put("customer_id", customer_id);

                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            deleteCart_fromDatabase(cartList.get(position).getPid());
            cartList.remove(position);
            setNewTotalPrice();
            adapter.notifyDataSetChanged();
        }
    };

    private void deleteCart_fromDatabase(final String pid) {
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

                params.put("operation", "remove_item");
                params.put("customer_id", customer_id);
                params.put("p_id", pid);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }


}


