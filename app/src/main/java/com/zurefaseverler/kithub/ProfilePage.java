package com.zurefaseverler.kithub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.zurefaseverler.kithub.StartUp.HOST;

public class ProfilePage extends AppCompatActivity  {

    static String id;
    private TextView twMail;
    private TextView twName;
    private ImageView imageView;

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        id = Integer.toString(sharedPref.getInt("id",-1));

        getProfileInfo();

        Button backToMain = findViewById(R.id.back_to_main);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePage.this, MainActivity.class));
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ProfilePage.this);
                alert.setMessage(R.string.profile_page_log_out);
                alert.setCancelable(true);
                alert.setNegativeButton(R.string.profile_page_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton(R.string.profile_page_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        int loggedIn = sharedPref.getInt("id", -1);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.remove("id");
                        editor.remove("name");
                        editor.apply();
                        
                        logOutToLog(loggedIn, new VolleyResponseListener() {
                            @Override
                            public void onResponse(String response) throws JSONException {

                            }
                        });

                        Toast.makeText(getApplicationContext(), R.string.logout_success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfilePage.this, MainActivity.class));
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
                startActivityForResult(new Intent(ProfilePage.this, ProfileInfo.class),1);
            }
        });

        Button comments = findViewById(R.id.yorumlarım);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProfilePage.this, UserCommentsHistory.class),1);
            }
        });


        findViewById(R.id.profilePage_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilePage.this, CartActivity.class);
                startActivity(i);
            }
        });

        Button orderHis = findViewById(R.id.order_history_btn);
        orderHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilePage.this, OrderHistory.class);
                i.putExtra("customer_id",id);
                startActivity(i);
            }
        });

    }

    public void logOutToLog(final int customer_id, final VolleyResponseListener listener){
        System.out.println(customer_id);
        String url = HOST + "save_logout.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(customer_id));
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            String name = data.getStringExtra("name");
            String mail = data.getStringExtra("mail");
            String image = data.getStringExtra("image");

            twName.setText(name);
            twMail.setText(mail);

            String[] temp = image.split("html/");
            Picasso.get().load(HOST + temp[1]).transform(new CircleTransform()).into(imageView);
        }
    }

    private void getProfileInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = HOST + "profile_page.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            ProfileInfo.setHashMap();
                            ProfileInfo.getInformation().put("id", jsonObject.getString("id"));
                            ProfileInfo.getInformation().put("name", jsonObject.getString("complete_name"));
                            ProfileInfo.getInformation().put("phoneNumber", jsonObject.getString("phone"));
                            ProfileInfo.getInformation().put("email", jsonObject.getString("e_mail"));
                            ProfileInfo.getInformation().put("password", jsonObject.getString("user_password"));
                            ProfileInfo.getInformation().put("address", jsonObject.getString("address"));
                            ProfileInfo.getInformation().put("imagePath", jsonObject.getString("image"));

                            twMail = findViewById(R.id.email);
                            twName = findViewById(R.id.name);
                            imageView = findViewById(R.id.profilePage_photo);

                            twMail.setText(jsonObject.getString("e_mail"));
                            twName.setText(jsonObject.getString("complete_name"));

                            String[] temp = jsonObject.getString("image").split("html/");
                            Picasso.get().load(HOST + temp[1]).transform(new CircleTransform()).into(imageView);

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
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                params.put("operation", "1");
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.go_back) {
            onBackPressed();
        }
    }
}
