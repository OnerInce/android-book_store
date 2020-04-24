
package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;



/*  Toast.makeText(context, "test toast message",Toast.LENGTH_SHORT).show();   */



public class yazarlar extends AppCompatActivity implements View.OnClickListener{

    private int son_Expand_group = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazarlar);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                onBackPressed();
                break;

        }
    }

    public void yazara_git(View view) {
        Intent intent = new Intent(getApplicationContext(), yazarlar_sayfasi.class);
        startActivity(intent);
    }
}
