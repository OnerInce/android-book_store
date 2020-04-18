package com.zurefaseverler.kithub;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class login extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue mQueue;
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

        /*Eklemelerim*/
        mQueue = Volley.newRequestQueue(this);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText givenEmail = findViewById(R.id.email);
                EditText givenPass = findViewById(R.id.password);
                String email = givenEmail.getText().toString();
                String pass = givenPass.getText().toString();
                loginUser(email,pass);
            }
        });

        /*-----------*/

        Typeface nexa = Typeface.createFromAsset(getAssets(),"nexa_light.otf");
        Typeface nexa_bold = Typeface.createFromAsset(getAssets(),"nexa_bold.otf");

    }
    private void loginUser(final String email, final String pass) {
        String url = "http://18.204.251.116/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String mail = jsonObject.getString("e_mail");
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Success. Email="+mail,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"başarısız",Toast.LENGTH_SHORT).show();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("e_mail",email);
                params.put("user_password",pass);
                return params;
            }
        };
        mQueue.add(request);
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
