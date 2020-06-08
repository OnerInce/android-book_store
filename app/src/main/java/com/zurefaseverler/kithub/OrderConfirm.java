package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirm extends AppCompatActivity {

    List<OrderConfirmOrders> list = new ArrayList<>();

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        getOrders(new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                RecyclerView view_order = findViewById(R.id.confirm_order_recycler_view);
                OrderConfirmAdapter adapter_order = new OrderConfirmAdapter(OrderConfirm.this, list);
                RecyclerView.LayoutManager layoutManager_order = new LinearLayoutManager(OrderConfirm.this);
                view_order.setLayoutManager(layoutManager_order);
                view_order.setAdapter(adapter_order);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_order_confirm);

        getOrders(new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                RecyclerView view_order = findViewById(R.id.confirm_order_recycler_view);
                OrderConfirmAdapter adapter_order = new OrderConfirmAdapter(OrderConfirm.this, list);
                RecyclerView.LayoutManager layoutManager_order = new LinearLayoutManager(OrderConfirm.this);
                view_order.setLayoutManager(layoutManager_order);
                view_order.setAdapter(adapter_order);
            }
        });
    }

    public void getOrders(final VolleyResponseListener listener){
        String url = "http://18.204.251.116/get_waiting_orders.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String order_id = jsonObject.getString("order_id");
                                String userName = jsonObject.getString("name");
                                String orderTime = jsonObject.getString("order_time");
                                String totalPrice = jsonObject.getString("total_price");
                                String address = jsonObject.getString("address");
                                String shipperName = jsonObject.getString("shipper_name");
                                String bookCount = jsonObject.getString("book_count");

                                OrderConfirmOrders temp = new OrderConfirmOrders(order_id, userName, orderTime, bookCount, Float.parseFloat(totalPrice), address, shipperName, "-2");
                                list.add(temp);
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
                });
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

}
