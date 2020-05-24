package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirm extends AppCompatActivity {

    List<OrderConfirmOrders> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        fill();
        RecyclerView view = findViewById(R.id.confirm_order_recycler_view);
        OrderConfirmAdapter adapter = new OrderConfirmAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);

    }

    public void fill(){

        for(int i = 0; i < 9; i++){
            OrderConfirmOrders temp = new OrderConfirmOrders("92", "18", "12.12.2020", "8", "152.86");
            list.add(temp);
        }

    }

}
