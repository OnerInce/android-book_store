package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class start_up extends AppCompatActivity {
    RequestQueue mQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);

        Thread timeTread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                    fillArrays();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(start_up.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };
        timeTread.start();
    }
    public void fillArrays(){
        adapter_kategoriler.categories = new ArrayList<>();
        adapter_kategoriler.bookTypes = new HashMap<>();

        mQueue = Volley.newRequestQueue(this);
        String url = "http://18.204.251.116/categories.php";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Iterator<String> keys = jsonObject.keys();
                            while(keys.hasNext()){
                                adapter_kategoriler.categories.add(keys.next());
                            }
                            for(int i =0 ;i<adapter_kategoriler.categories.size();i++){
                                JSONArray jsonArray = jsonObject.getJSONArray(adapter_kategoriler.categories.get(i));

                                String currentCategory = adapter_kategoriler.categories.get(i);
                                ArrayList<String> arr = new ArrayList<>();
                                for(int j=0;j<jsonArray.length();j++){
                                    arr.add(jsonArray.getString(j));
                                }
                                adapter_kategoriler.bookTypes.put(currentCategory,arr);
                            }
                        } catch (JSONException e) {

                        }


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
    protected void onPause(){
        super.onPause();
        finish();
    }
}
