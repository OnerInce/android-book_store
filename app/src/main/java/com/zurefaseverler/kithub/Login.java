package com.zurefaseverler.kithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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

import static com.zurefaseverler.kithub.StartUp.HOST;


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
        String url = HOST + "login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){

                                String id = jsonObject.getString("id");
                                String isAdmin = jsonObject.getString("is_admin");
                                String name = jsonObject.getString("complete_name");

                                if (isAdmin.equals("1")){
                                    Toast.makeText(getApplicationContext(), R.string.login_admin, Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("id", Integer.parseInt(id));
                                editor.putInt("isAdmin", Integer.parseInt(isAdmin));
                                editor.putString("name", name);
                                editor.apply();

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("isAdmin", isAdmin);

                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), R.string.login_fail,Toast.LENGTH_SHORT).show();
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


        }
    }

    public void sign_up_attempt(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
}
