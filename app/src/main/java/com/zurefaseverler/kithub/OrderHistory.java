package com.zurefaseverler.kithub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {



    List<OrderHistoryObject> orderList = new ArrayList<>();


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        listFill();

        RecyclerView orderRecycler = findViewById(R.id.order_list);
        OrderHistoryAdapter adapterOrder= new OrderHistoryAdapter(this, orderList);
        RecyclerView.LayoutManager orderHistoryLayout=new LinearLayoutManager(this);
        orderRecycler.setLayoutManager( orderHistoryLayout);
        orderRecycler.setAdapter(adapterOrder);

    }


    public void listFill(){


        OrderHistoryObject x1= new OrderHistoryObject("Zülfü Livaneli - Huzursuzluk","25 TL","20 Kasım 2020",R.drawable.cart_huzursuzluk);
        OrderHistoryObject x2= new OrderHistoryObject("Zülfü Livaneli - Huzursuzluk","25 TL","20 Kasım 2020",R.drawable.cart_huzursuzluk);
        OrderHistoryObject x3= new OrderHistoryObject("Zülfü Livaneli - Huzursuzluk","25 TL","20 Kasım 2020",R.drawable.cart_huzursuzluk);


        orderList.add(x1);
        orderList.add(x2);
        orderList.add(x3);



    }



}
