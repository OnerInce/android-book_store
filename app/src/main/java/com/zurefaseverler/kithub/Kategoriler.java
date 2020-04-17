package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;



/*  Toast.makeText(context, "test toast message",Toast.LENGTH_SHORT).show();   */



public class Kategoriler extends AppCompatActivity implements View.OnClickListener{

    private int son_Expand_group = -1;

    private RequestQueue mQueue;
    private ExpandableListView kategoriler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategoriler);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        kategoriler = (ExpandableListView) findViewById(R.id.acilirKategoriler);


        adapter_kategoriler a = new adapter_kategoriler(this,adapter_kategoriler.categories,adapter_kategoriler.bookTypes);
        kategoriler.setAdapter(a);

        kategoriler.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(son_Expand_group != -1 && groupPosition != son_Expand_group){

                    kategoriler.collapseGroup(son_Expand_group);

                }
                son_Expand_group = groupPosition;
            }
        });


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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                onBackPressed();
                break;
        }
    }
}
