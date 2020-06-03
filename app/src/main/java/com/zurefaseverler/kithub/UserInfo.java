package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo extends AppCompatActivity {
    static class User{
        private String id, name, phone, e_mail, address, image;
        User(String id, String name, String phone, String e_mail, String address, String image) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.e_mail = e_mail;
            this.address = address;
            this.image = image;
        }
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getPhone() {
            return phone;
        }
        public String getE_mail() {
            return e_mail;
        }
        public String getAddress() {
            return address;
        }
        public String getImage() {
            return image;
        }
    }
    User thisUser;
    List<OrderConfirmOrders> order_list = new ArrayList<>();
    List<CommentObj> comment_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        thisUser = getUserInfo(getIntent());
        setUserInfo();

        fill_order(thisUser.getId(), new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                RecyclerView view_order = findViewById(R.id.order_details_recycler_view);
                OrderConfirmAdapter adapter_order = new OrderConfirmAdapter(UserInfo.this, order_list);
                RecyclerView.LayoutManager layoutManager_order = new LinearLayoutManager(UserInfo.this);
                view_order.setLayoutManager(layoutManager_order);
                view_order.setAdapter(adapter_order);
            }
        });


        fill_comment(thisUser.getId(), new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                RecyclerView view_comment = findViewById(R.id.comment_details_recycler_view);
                CommentDetailsAdapter adapter_comment = new CommentDetailsAdapter(UserInfo.this, comment_list);
                RecyclerView.LayoutManager layoutManager_comment = new LinearLayoutManager(UserInfo.this);
                view_comment.setLayoutManager(layoutManager_comment);
                view_comment.setAdapter(adapter_comment);
            }
        });

    }



    private User getUserInfo(Intent intent) {
        return new User(intent.getStringExtra("user_id"),
                intent.getStringExtra("name"),
                intent.getStringExtra("phone"),
                intent.getStringExtra("e_mail"),
                intent.getStringExtra("address"),
                intent.getStringExtra("image"));
    }
    private void setUserInfo() {
        TextView name = findViewById(R.id.searchUser_name);
        TextView phone = findViewById(R.id.searchUser_phone);
        TextView e_mail = findViewById(R.id.searchUser_email);
        TextView address = findViewById(R.id.searchUser_address);
        ImageView image = findViewById(R.id.profilePage_photo);

        name.setText(thisUser.getName());
        phone.setText(thisUser.getPhone());
        e_mail.setText(thisUser.getE_mail());
        address.setText(thisUser.getAddress());

        String[] temp = thisUser.getImage().split("html/");
        Picasso.get().load("http://18.204.251.116/" + temp[1]).transform(new CircleTransform()).into(image);
    }

    public void fill_order(final String user_id, final VolleyResponseListener listener){
        String url = "http://18.204.251.116/get_user_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            order_list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String order_id = jsonObject.getString("order_id");
                                String orderTime = jsonObject.getString("order_time");
                                String totalPrice = jsonObject.getString("total_price");
                                String address = jsonObject.getString("address");
                                String shipperName = jsonObject.getString("shipper_name");
                                String bookCount = jsonObject.getString("book_count");

                                OrderConfirmOrders temp = new OrderConfirmOrders(order_id, user_id, orderTime, bookCount, totalPrice, address, shipperName);
                                order_list.add(temp);
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
                params.put("user_id", user_id);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void fill_comment(final String user_id, final VolleyResponseListener listener){
        String url = "http://18.204.251.116/user_comments.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String title = jsonArray.getJSONObject(i).getString("title");
                                String commentText = jsonArray.getJSONObject(i).getString("review_text");
                                String commentDate = jsonArray.getJSONObject(i).getString("review_date").split(" ")[0];
                                String rate = jsonArray.getJSONObject(i).getString("rating");
                                CommentObj temp = new CommentObj(title, commentText, commentDate, Integer.parseInt(rate));
                                comment_list.add(temp);
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
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", user_id);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
