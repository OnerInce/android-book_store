package com.zurefaseverler.kithub;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrderDetailUser extends AppCompatActivity {

    ArrayList<OrderDetailUserObject> orderDetailList = new ArrayList<>();


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);


        listFill(getIntent().getStringExtra("order_id"), new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                RecyclerView orderDetailRecycler = findViewById(R.id.detail_recycler);
                OrderDetailUserAdapter adapterDetailOrder= new OrderDetailUserAdapter(OrderDetailUser.this, orderDetailList);
                RecyclerView.LayoutManager orderDetailLayout=new LinearLayoutManager(OrderDetailUser.this);
                orderDetailRecycler.setLayoutManager(orderDetailLayout);
                orderDetailRecycler.setAdapter(adapterDetailOrder);

                String[] temp = response.split("%");
                String address = temp[0];
                String shipper = temp[1];
                String is_confirmed = temp[2];

                TextView showAddress = findViewById(R.id.showAddress);
                showAddress.setText(address);

                ImageView shipperImage = findViewById(R.id.CargoImage);

                if(shipper.equals("TNT")){
                    shipperImage.setImageResource(R.drawable.shipping1);
                }else if(shipper.equals("UPS")){
                    shipperImage.setImageResource(R.drawable.shipping2);
                }

                TextView shippingStatus = findViewById(R.id.shippingStatus);
                switch (is_confirmed) {
                    case "1":
                        shippingStatus.setText("Hazırlanıyor..");
                        break;
                    case "0":
                        shippingStatus.setText("Onay Bekliyor..");
                        break;
                    case "-1":
                        shippingStatus.setText("Sipariş reddedildi.");
                        break;
                }
            }
        });



    }

    public void listFill(final String order_id, final VolleyResponseListener listener){
        String url = "http://18.204.251.116/get_details_by_order_id.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String address = jsonObject.getString("address");
                            String shipper = jsonObject.getString("shipper");
                            String is_confirmed = jsonObject.getString("is_confirmed");

                            JSONArray jsonArray = jsonObject.getJSONArray("books");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject bookObject = jsonArray.getJSONObject(i);
                                String bookName = bookObject.getString("book_name");
                                String price = bookObject.getString("price");
                                String quantity = bookObject.getString("quantity");
                                String imagePath = bookObject.getString("image");

                                OrderDetailUserObject temp = new OrderDetailUserObject(bookName, price, imagePath, quantity);
                                orderDetailList.add(temp);
                            }
                            listener.onResponse(address + "%" + shipper + "%" + is_confirmed);

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
                params.put("order_id", order_id);
                return params;
            }
        };

        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }



}
