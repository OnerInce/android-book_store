package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class yazarlar_sayfasi extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazarlar_sayfasi);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);




    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                onBackPressed();
                break;

        }
    }

    public void kitaba_git(View view) {
        Intent intent = new Intent(getApplicationContext(), urun_sayfasi.class);
        startActivity(intent);
    }
}

