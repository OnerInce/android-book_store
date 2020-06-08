package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    Button accept, reject;

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        userName = findViewById(R.id.order_details_userid);
        orderID = findViewById(R.id.order_details_orderid);
        orderDate = findViewById(R.id.order_details_date);
        totalAmount = findViewById(R.id.order_details_amount);

        accept = findViewById(R.id.order_details_button_confirm);
        reject = findViewById(R.id.order_details_button_cancel);

        Intent intent = getIntent();

        final String order_id = intent.getStringExtra("order_id");
        String date = intent.getStringExtra("date");
        String totalPrice = intent.getStringExtra("total_price");
        String name = intent.getStringExtra("name");
        String caller = intent.getStringExtra("status");

        if (caller.equals("hide")){
            accept.setVisibility(View.GONE);
            reject.setVisibility(View.GONE);

        }

        userName.setText(name);
        orderID.setText("Sipariş ID: " + order_id);
        orderDate.setText("Tarih: " + date.split(" ")[0]);
        totalAmount.setText("Toplam fiyat: " + Float.parseFloat(totalPrice) + " ₺");

        final Intent i = new Intent(OrderDetails.this, OrderConfirm.class);

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

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderDecision(order_id, "1", new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if(success.equals("1"))
                            Toast.makeText(getApplicationContext(), R.string.order_confirm_yes, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), R.string.order_confirm_fail, Toast.LENGTH_SHORT).show();
                        i.putExtra("finished_order", order_id);
                        startActivity(i);
                    }
                });
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderDecision(order_id, "0", new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if(success.equals("1"))
                            Toast.makeText(getApplicationContext(), R.string.order_confirm_no, Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), R.string.order_confirm_fail, Toast.LENGTH_LONG).show();

                        i.putExtra("finished_order", order_id);
                        startActivity(i);
                    }
                });
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
                                float discountFloat;
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

    public void sendOrderDecision(final String order_id, final String decision, final VolleyResponseListener listener){
        String url = "http://18.204.251.116/order_decision.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
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
                params.put("decision", decision);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
