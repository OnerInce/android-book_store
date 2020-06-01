package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }



    // Admin ana sayfa button foksiyonları burada çalışıyor                                 #################
    public void add_book(View view){

        i = new Intent(this, AddBook.class);
        startActivity(i);

    }

    public void delete_book(View view){

        i = new Intent(this, DeleteBook.class);
        startActivity(i);


    }

    public void update_book(View view){
        i = new Intent(this, UpdateBookSearch.class);
        startActivity(i);

    }

    public void stock_view(View view){

        i = new Intent(this, StockTracking.class);
        startActivity(i);
    }

    public void user_traffic_log(View view){




    }

    public void add_category(View view){

        i = new Intent(this, AddCategory.class);
        startActivity(i);

    }

    public void delete_category(View view){

        i = new Intent(this, DeleteCategory.class);
        startActivity(i);

    }

    public void confirm_order(View view){

        i = new Intent(this, OrderConfirm.class);
        startActivity(i);

    }

    public void delete_author(View view){

        i = new Intent(this, OrderDetails.class);
        startActivity(i);

    }


    public void discount_publisher(View view){

        i = new Intent(this, DiscountBookSearch.class);
        startActivity(i);
      
    }

    public void show_users(View view){

        i = new Intent(this, UserShow.class);
        startActivity(i);

    }

    //                                                                                      ###################
}
