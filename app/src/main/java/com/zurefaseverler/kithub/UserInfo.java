package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

            }
        });
        RecyclerView view_order = findViewById(R.id.order_details_recycler_view);
        OrderConfirmAdapter adapter_order = new OrderConfirmAdapter(this, order_list);
        RecyclerView.LayoutManager layoutManager_order = new LinearLayoutManager(this);
        view_order.setLayoutManager(layoutManager_order);
        view_order.setAdapter(adapter_order);

        fill_comment();
        RecyclerView view_comment = findViewById(R.id.comment_details_recycler_view);
        CommentDetailsAdapter adapter_comment = new CommentDetailsAdapter(this, comment_list);
        RecyclerView.LayoutManager layoutManager_comment = new LinearLayoutManager(this);
        view_comment.setLayoutManager(layoutManager_comment);
        view_comment.setAdapter(adapter_comment);
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

    public void fill_order(String id, VolleyResponseListener listener){
        for(int i = 0 ; i < 6 ; i++){
            OrderConfirmOrders temp = new OrderConfirmOrders("order_id", "userid", "14.03.1879", "5 adet alindi", "100");
            order_list.add(temp);
        }
    }

    public void fill_comment(){
        for(int i = 0 ; i < 9 ; i++){
            CommentObj temp = new CommentObj("user1", "Kitap gayet basarili.", "14.03.1879", 4);
            comment_list.add(temp);
        }
    }
}
