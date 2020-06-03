package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentCorrect extends AppCompatActivity {


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_correct);

        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentCorrect.this, MainActivity.class);
                startActivity(i);
            }
        });




    }

    public void onBackPressed(){
        Intent i = new Intent(PaymentCorrect.this, MainActivity.class);
        startActivity(i);
    }

}
