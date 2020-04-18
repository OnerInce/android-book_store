package com.zurefaseverler.kithub;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class add_book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final EditText i_1 = findViewById(R.id.ISBN);
        final EditText i_2 = findViewById(R.id.admin_add_book_title);
        final EditText i_3 = findViewById(R.id.admin_add_book_author);
        final EditText i_4 = findViewById(R.id.admin_add_book_stock_quantity);
        final EditText i_5 = findViewById(R.id.admin_add_book_category);
        final EditText i_6 = findViewById(R.id.admin_add_book_type);
        final EditText i_7 = findViewById(R.id.admin_add_book_price);
        final EditText i_8 = findViewById(R.id.admin_add_book_ozet);

        Button eklebuttonu = findViewById(R.id.add_book_ekle_button);
        eklebuttonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ISBN = i_1.getText().toString();
                String book_name = i_2.getText().toString();
                String author = i_3.getText().toString();
                String quantity = i_4.getText().toString();
                String category = i_5.getText().toString();
                String type = i_6.getText().toString();
                String price = i_7.getText().toString();
                String ozet = i_8.getText().toString();

                if(ISBN.equals("") | book_name.equals("") | author.equals("") | quantity.equals("") | category.equals("") | type.equals("") | price.equals("") |
                        ozet.equals("") ){

                    Toast.makeText(add_book.this, "Tüm bilgileri girdiğinizden emin olun !",Toast.LENGTH_LONG).show();
                    return;
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(add_book.this);
                builder.setCancelable(true);
                builder.setMessage(book_name +" adlı kitabı eklemek istediğinize emin misiniz?");
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



                        //kitap ekleme kısmı buraya




                        Toast.makeText(add_book.this, "Kitap başarıyla eklendi",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

            }
        });
    }
}
