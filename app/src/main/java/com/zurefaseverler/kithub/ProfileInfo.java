package com.zurefaseverler.kithub;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileInfo extends AppCompatActivity {
    private static final int IMAGE_CODE = 100;
    private static final int PERMISSION_CODE = 101;

    private static HashMap<String,String> information;
    private EditText info_name;
    private EditText info_mail;
    private EditText info_phone;
    private EditText info_address;
    private ImageView profileImage;
    private Bitmap bitmap;
    private String imagePath;
    private boolean isPhotoChanged;
    public static void setHashMap() {
        ProfileInfo.information = new HashMap<>();
    }
    public static HashMap<String, String> getInformation() {
        return information;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        info_name = findViewById(R.id.info_name);
        info_mail = findViewById(R.id.info_mail);
        info_phone = findViewById(R.id.info_phone);
        info_address = findViewById(R.id.info_address);
        profileImage = findViewById(R.id.profile_image);

        setInformation();
        findViewById(R.id.passwordChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileInfo.this);
                View mView = getLayoutInflater().inflate(R.layout.password_change_popup, null);


                final EditText newPass = (EditText) mView.findViewById(R.id.newPass);
                final EditText oldPass = (EditText) mView.findViewById(R.id.oldPass);
                Button savePass = (Button) mView.findViewById(R.id.savePass);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                savePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(oldPass.getText().toString().equals(information.get("password"))){
                            information.replace("password",newPass.getText().toString());
                            dialog.cancel();
                        }
                    }
                });
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_CODE);
                }
                else{
                    //Permission already granted
                    pickImage();
                }

            }
        });

        findViewById(R.id.saveChanges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = alertDialog();
                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        information.replace("name",info_name.getText().toString());
                        information.replace("email",info_mail.getText().toString());
                        information.replace("phoneNumber",info_phone.getText().toString());
                        information.replace("address",info_address.getText().toString());
                        updateDatabase();
                        Toast.makeText(ProfileInfo.this,"Değişiklikler Kaydedildi",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    private void updateDatabase() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://18.204.251.116/profile_page.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            information.replace("imagePath",jsonObject.getString("newFilePath"));
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
                HashMap<String,String> params = new HashMap<>();
                params.put("complete_name",information.get("name"));
                params.put("phone",information.get("phoneNumber"));
                params.put("e_mail",information.get("email"));
                params.put("address",information.get("address"));
                params.put("user_password",information.get("password"));
                params.put("id",ProfilePage.id);
                params.put("operation","2");
                if(!isPhotoChanged) params.put("encodedImage","empty");
                else    params.put("encodedImage",imageToString(bitmap));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private AlertDialog.Builder alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileInfo.this);
        builder.setCancelable(true);
        builder.setMessage("Değişiklikleri kaydetmek istediğinize emin misiniz?");
        builder.setTitle("Değişiklikleri Kaydet");
        return builder;
    }

    private void setInformation() {
        info_name.setText(information.get("name"));
        info_mail.setText(information.get("email"));
        info_phone.setText(information.get("phoneNumber"));
        info_address.setText(information.get("address"));

        String[] temp = information.get("imagePath").split("html/");
        Picasso.get().load("http://18.204.251.116/"+temp[1])
                .transform(new CircleTransform()).into(profileImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImage();
        }
        else {
            Toast.makeText(this,"Bu yetkiyi ayarlardan değişebilirsin.",Toast.LENGTH_SHORT).show();

        }
    }

    private void pickImage() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i,"pick"),IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_CODE) {
            if (data != null) {
                ImageView imageView = findViewById(R.id.profile_image);
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isPhotoChanged = true;
                Picasso.get().load(selectedImage).transform(new CircleTransform()).into(imageView);
            }
        }
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
    }
}