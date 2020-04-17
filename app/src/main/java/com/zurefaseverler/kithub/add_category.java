package com.zurefaseverler.kithub;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class add_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        final String s = "";
        final EditText category_edit = findViewById(R.id.admin_add_category_category);
        final EditText type_edit = findViewById(R.id.admin_add_category_book_type);

        Button add = findViewById(R.id.ekle);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = category_edit.getText().toString();
                String type = type_edit.getText().toString();


                AlertDialog.Builder builder = new AlertDialog.Builder(add_category.this);
                builder.setCancelable(true);
                builder.setMessage(category +" kategorisini " + type + " kitap türünü eklemek istediğinize emin misiniz?");
                builder.setTitle("EKLEME ONAYI");

                builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //kategori ekleme kısmı buraya


                        Toast.makeText(add_category.this, "Kategori başarıyla eklendi",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

                category_edit.setText(s);
                type_edit.setText(s);
            }
        });



    }





}
