
package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



/*  Toast.makeText(context, "test toast message",Toast.LENGTH_SHORT).show();   */



public class Authors extends AppCompatActivity implements View.OnClickListener{

    private int son_Expand_group = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);




    }

    private void getBookInfo(final String title) {
        String url = "http://18.204.251.116/books.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            /*book_id = jsonObject.getInt("id");
                            ISBN = jsonObject.getString("ISBN");
                            //title = jsonObject.getString("title");
                            author_id = jsonObject.getInt("author_id");
                            stockQuantity = jsonObject.getInt("stock_quantity");
                            category_id = jsonObject.getInt("category_id");
                            bookType_id = jsonObject.getInt("book_type_id");
                            price = jsonObject.getInt("price");
                            sales = jsonObject.getInt("sales");
                            rating = Float.parseFloat(jsonObject.getString("rating"));
                            ratedCount = jsonObject.getInt("number_of_people_rated");
                            summary = jsonObject.getString("summary");
                            image = jsonObject.getString("image");*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("title",title);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(request);
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
        Intent intent = new Intent(getApplicationContext(), AuthorsPage.class);
        startActivity(intent);
    }
}
