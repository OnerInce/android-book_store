package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    List<OrderDetailsBookObj> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        fill();
        RecyclerView view = findViewById(R.id.order_details_recycler_view);
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);

    }

    public void fill(){

        for(int i = 0 ; i < 8 ; i++){
            OrderDetailsBookObj temp = new OrderDetailsBookObj("haydar dümen ile iftar saati", "12", 72);
            list.add(temp);
        }

    }
}
