package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zurefaseverler.kithub.StartUp.HOST;

public class OrderHistory extends AppCompatActivity {
    List<OrderHistoryObject> orderList = new ArrayList<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        listFill(getIntent().getStringExtra("customer_id"), new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                if(response.equals("empty"))
                    Toast.makeText(getApplicationContext(), "Siparişiniz bulunmamaktadır", Toast.LENGTH_SHORT).show();
                else{
                    RecyclerView orderRecycler = findViewById(R.id.order_list);
                    OrderHistoryAdapter adapterOrder = new OrderHistoryAdapter(OrderHistory.this, orderList);
                    RecyclerView.LayoutManager orderHistoryLayout=new LinearLayoutManager(OrderHistory.this);
                    orderRecycler.setLayoutManager( orderHistoryLayout);
                    orderRecycler.setAdapter(adapterOrder);
                    adapterOrder.setOnItemClickListener(new OrderHistoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent i = new Intent(OrderHistory.this, OrderDetailUser.class);
                            i.putExtra("order_id", orderList.get(position).getOrder_id());
                            startActivity(i);
                        }
                    });
                }
            }
        });

/*
        */
    }

    public void listFill(final String customer_id, final VolleyResponseListener listener){
        String url = HOST + "hget_orders_by_user_id.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String order_id = jsonObject.getString("order_id");
                                String orderTime = jsonObject.getString("order_time");
                                String imagePath = jsonObject.getString("image");
                                String bookCount = jsonObject.getString("book_count");
                                String bookName = jsonObject.getString("book_name");
                                String singlePrice = jsonObject.getString("single_price");

                                OrderHistoryObject temp = new OrderHistoryObject(bookName, singlePrice, orderTime, imagePath, bookCount, order_id);

                                orderList.add(temp);
                            }
                            if(orderList.isEmpty())
                                listener.onResponse("empty");
                            else
                                listener.onResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("customer_id", customer_id);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
