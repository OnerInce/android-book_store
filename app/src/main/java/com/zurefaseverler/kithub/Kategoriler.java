package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;



/*  Toast.makeText(context, "test toast message",Toast.LENGTH_SHORT).show();   */



public class Kategoriler extends AppCompatActivity implements View.OnClickListener{

    private int son_Expand_group = -1;

    private ArrayList<String> bookCategories;
    private ArrayList<String[]> bookTypes;
    private RequestQueue mQueue;
    private ExpandableListView kategoriler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategoriler);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        bookCategories = new ArrayList<>();
        bookTypes = new ArrayList<>();

        kategoriler = (ExpandableListView) findViewById(R.id.acilirKategoriler);

        fillArrays();
        adapter_kategoriler a = new adapter_kategoriler(this,bookCategories,bookTypes);


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

    public void fillArrays(){
        mQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.36/KitHub/categories.php";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Iterator<String> keys = jsonObject.keys();
                            while(keys.hasNext()){
                                bookCategories.add(keys.next());
                            }
                            for(int i =0 ;i<bookCategories.size();i++){
                                JSONArray jsonArray = jsonObject.getJSONArray(bookCategories.get(i));
                                String[] arr = new String[jsonArray.length()];
                                for(int j=0;j<jsonArray.length();j++){
                                    arr[j] = jsonArray.getString(j);
                                }
                                bookTypes.add(arr);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter_kategoriler a = new adapter_kategoriler(Kategoriler.this,bookCategories,bookTypes);
                        kategoriler.setAdapter(a);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mQueue.add(request);
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
