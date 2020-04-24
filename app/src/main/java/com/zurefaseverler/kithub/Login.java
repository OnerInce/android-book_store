package com.zurefaseverler.kithub;

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


public class Login extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);
        TextView forgot = findViewById(R.id.unuttum);
        forgot.setOnClickListener(this);
        TextView register =  findViewById(R.id.kaydol);
        register.setOnClickListener(this);

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
            protected Map<String, String> getParams(){
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
                Toast.makeText(Login.this, "(0)))",Toast.LENGTH_SHORT).show();
                break;

            case R.id.kaydol:
                Toast.makeText(Login.this, "Kayıt sayfasına yönlendirme yapılacak",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
