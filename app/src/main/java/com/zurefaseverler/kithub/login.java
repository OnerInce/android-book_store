package com.zurefaseverler.kithub;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class login extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);
        TextView unuttum = findViewById(R.id.unuttum);
        unuttum.setOnClickListener(this);
        TextView kaydol =  findViewById(R.id.kaydol);
        kaydol.setOnClickListener(this);


        Typeface nexa = Typeface.createFromAsset(getAssets(),"nexa_light.otf");
        Typeface nexa_bold = Typeface.createFromAsset(getAssets(),"nexa_bold.otf");

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

            case R.id.kaydol:
                Toast.makeText(login.this, "Kayıt sayfasına yönlendirme yapılacak",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
