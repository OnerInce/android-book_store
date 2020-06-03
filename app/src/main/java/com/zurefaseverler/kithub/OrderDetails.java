package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {

    List<OrderDetailsBookObj> list = new ArrayList<>();
    TextView userName, orderID, orderDate, totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        userName = findViewById(R.id.order_details_userid);
        orderID = findViewById(R.id.order_details_orderid);
        orderDate = findViewById(R.id.order_details_date);
        totalAmount = findViewById(R.id.order_details_amount);

        Intent intent = getIntent();

        String order_id = intent.getStringExtra("order_id");
        String date = intent.getStringExtra("date");
        String totalPrice = intent.getStringExtra("total_price");
        String name = intent.getStringExtra("name");

        userName.setText(name);
        orderID.setText("Sipariş ID: " + order_id);
        orderDate.setText("Tarih: " + date.split(" ")[0]);
        totalAmount.setText("Toplam fiyat: " + Float.parseFloat(totalPrice) + " ₺");

        getOrderDetails(order_id, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                RecyclerView view = findViewById(R.id.order_details_recycler_view);
                OrderDetailsAdapter adapter = new OrderDetailsAdapter(OrderDetails.this,list);
                RecyclerView.LayoutManager layoutManager_order = new LinearLayoutManager(OrderDetails.this);
                view.setLayoutManager(layoutManager_order);
                view.setAdapter(adapter);
            }
        });

    }

    public void getOrderDetails(final String order_id, final VolleyResponseListener listener){
        String url = "http://18.204.251.116/get_order_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                float discountFloat = 0;
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                try{
                                    discountFloat = Float.parseFloat(jsonObject.getString("discount"));
                                } catch (NumberFormatException ex) {
                                    discountFloat = (float) 0.0;
                                }

                                String title = jsonObject.getString("title");
                                String quantity = jsonObject.getString("quantity");
                                String single_price = jsonObject.getString("single_price");

                                OrderDetailsBookObj temp = new OrderDetailsBookObj(title, quantity, Float.parseFloat(single_price), discountFloat);
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("order_id", order_id);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
