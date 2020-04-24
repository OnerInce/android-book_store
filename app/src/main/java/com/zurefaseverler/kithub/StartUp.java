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

public class StartUp extends AppCompatActivity {
    RequestQueue mQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        Thread timeTread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                    fillArrays();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(StartUp.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };
        timeTread.start();
    }
    public void fillArrays(){
        AdapterCategories.categories = new ArrayList<>();
        AdapterCategories.bookTypes = new HashMap<>();

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
                                AdapterCategories.categories.add(keys.next());
                            }
                            for(int i = 0; i< AdapterCategories.categories.size(); i++){
                                JSONArray jsonArray = jsonObject.getJSONArray(AdapterCategories.categories.get(i));

                                String currentCategory = AdapterCategories.categories.get(i);
                                ArrayList<String> arr = new ArrayList<>();
                                for(int j=0;j<jsonArray.length();j++){
                                    arr.add(jsonArray.getString(j));
                                }
                                AdapterCategories.bookTypes.put(currentCategory,arr);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
