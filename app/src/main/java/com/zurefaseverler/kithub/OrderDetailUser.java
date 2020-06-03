package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class OrderDetailUser extends AppCompatActivity {

    ArrayList<OrderDetailUserObject> orderDetailList = new ArrayList<>();


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        listFill();
        RecyclerView orderDetailRecycler = findViewById(R.id.detail_recycler);
        OrderDetailUserAdapter adapterDetailOrder= new OrderDetailUserAdapter(this, orderDetailList);
        RecyclerView.LayoutManager orderDetailLayout=new LinearLayoutManager(this);
        orderDetailRecycler.setLayoutManager(orderDetailLayout);
        orderDetailRecycler.setAdapter(adapterDetailOrder);

    }

    public void listFill(){


        OrderDetailUserObject x1= new OrderDetailUserObject("Zülfü Livaneli - Huzursuzluk","25 TL",R.drawable.cart_huzursuzluk);
        OrderDetailUserObject x2= new OrderDetailUserObject("Zülfü Livaneli - Huzursuzluk","25 TL",R.drawable.cart_huzursuzluk);
        OrderDetailUserObject x3= new OrderDetailUserObject("Zülfü Livaneli - Huzursuzluk","25 TL",R.drawable.cart_huzursuzluk);


        orderDetailList.add(x1);
        orderDetailList.add(x2);
        orderDetailList.add(x3);



    }



}
