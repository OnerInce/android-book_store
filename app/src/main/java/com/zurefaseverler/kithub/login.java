package com.zurefaseverler.kithub;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class login extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);
        TextView unuttum = findViewById(R.id.unuttum);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.go_back:
                onBackPressed();
                break;

            case R.id.unuttum:
                Toast.makeText(login.this, "(0)))",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
