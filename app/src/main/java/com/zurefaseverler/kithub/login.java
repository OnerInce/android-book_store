package com.zurefaseverler.kithub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class login extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button back_main = findViewById(R.id.back_main_login);
        back_main.setOnClickListener(this);
        TextView unuttum = findViewById(R.id.unuttum);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back_main_login:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                break;

            case R.id.unuttum:
                Toast.makeText(login.this, "(0)))",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
