package com.zurefaseverler.kithub;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInfo extends AppCompatActivity {
    private static final int IMAGE_CODE = 100;
    private static final int PERMISSION_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);


        findViewById(R.id.passwordChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileInfo.this);
                View mView = getLayoutInflater().inflate(R.layout.password_change_popup, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.etPassword);
                final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword2);
                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                Objects.requireNonNull(dialog.getWindow()).setLayout(1100,775);
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
                            Toast.makeText(ProfileInfo.this,
                                    "Başarılı",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(ProfileInfo.this,
                                    "Boşlukları doldurun",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        findViewById(R.id.profile_image).setOnClickListener(new View.OnClickListener() {
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
                CircleImageView imageView = findViewById(R.id.profile_image);
                imageView.setImageURI(data.getData());
            }
        }
    }
}