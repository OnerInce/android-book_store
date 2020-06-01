package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends AppCompatActivity {
    List<OrderConfirmOrders> order_list = new ArrayList<>();
    List<CommentObj> comment_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        fill_order();
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




    public void fill_order(){

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
