package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class urun_sayfasi extends AppCompatActivity implements View.OnClickListener {

    ViewGroup bilgi_Layout ;
    TextView ozet;
    TextView yazar;
    Button  button_Ozet;
    Button  button_yazar;
    boolean bool_ozet;
    boolean bool_yazar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urun_sayfasi);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        bilgi_Layout = findViewById(R.id.urun_sayfasi_yazar_ozet_layout);
        ozet = findViewById(R.id.urun_sayfasi_ozet_txt);
        yazar = findViewById(R.id.urun_sayfasi_yazar_txt);
        button_Ozet = findViewById(R.id.urun_sayfasi_ozet_button);
        button_yazar = findViewById(R.id.urun_sayfasi_yazar_button);
        button_yazar.setOnClickListener(this);
        button_Ozet.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.go_back:
                onBackPressed();
                break;

            case R.id.urun_sayfasi_ozet_button:

                bool_ozet = !bool_ozet;
                ozet.setVisibility(bool_ozet ? View.VISIBLE: View.GONE);

                break;
            case R.id.urun_sayfasi_yazar_button:

                bool_yazar = !bool_yazar;
                yazar.setVisibility(bool_yazar ? View.VISIBLE: View.GONE);

                break;
        }

    }
}
