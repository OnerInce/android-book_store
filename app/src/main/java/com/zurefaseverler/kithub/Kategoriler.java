package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class Kategoriler extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategoriler);


        ExpandableListView kategoriler = (ExpandableListView) findViewById(R.id.acilirKategoriler);
        kategoriler.setAdapter( new adapter_kategoriler(this));

        kategoriler.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent i = new Intent(getApplicationContext(), urun_listesi.class);
                String b = ((TextView) v).getText().toString();
                i.putExtra("mesaj", b);
                startActivity(i);

                return false;
            }
        });



    }
}
