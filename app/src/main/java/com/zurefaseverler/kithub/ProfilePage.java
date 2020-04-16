package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilePage extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        Button backToMain = findViewById(R.id.back_to_main);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ProfilePage.this);
                alert.setMessage("Çıkış yapmak istediğinize emin misiniz?");
                alert.setCancelable(true);
                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ProfilePage.this,MainActivity.class));
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });

        Button profileInfo = findViewById(R.id.profile_info);
        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePage.this,ProfileInfo.class));
            }
        });
    }
}
