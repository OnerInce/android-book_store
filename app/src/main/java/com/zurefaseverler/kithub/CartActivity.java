package com.zurefaseverler.kithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;
    private Button guncelle;
    private ImageButton back;
    ElegantNumberButton sayiButon;

    ArrayList<Cart> liste;

    AdapterCart adp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        liste = new ArrayList<>();
        listeDoldur();



        NextProcessBtn = (Button) findViewById(R.id.next_process_button);
        txtTotalAmount = (TextView) findViewById(R.id.price);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        sayiButon = (ElegantNumberButton)findViewById(R.id.product_quantity1);
        back = findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });



    }



    public void listeDoldur(){


        Cart list1=new Cart("1","Huzursuzluk","22","1","%20",R.drawable.cart_huzursuzluk);
        Cart list2=new Cart("2","Sefiller","22","1","%20",R.drawable.cart_huzursuzluk);
        Cart list3=new Cart("3","Selam","22","1","%20",R.drawable.cart_huzursuzluk);
        liste.add(list1);
        liste.add(list2);
        liste.add(list3);
        adp = new AdapterCart(this,liste);
        recyclerView.setAdapter(adp);




    }



    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            liste.remove(viewHolder.getAdapterPosition());
            adp.notifyDataSetChanged();
        }
    };












}
