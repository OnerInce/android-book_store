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




    }

    public void update_book(View view){




    }

    public void stock_view(View view){




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



    }

    //                                                                                      ###################
}
