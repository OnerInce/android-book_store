package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateBookInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        EditText i_1 = findViewById(R.id.ISBN);
        EditText i_2 = findViewById(R.id.admin_add_book_title);
        EditText i_3 = findViewById(R.id.admin_add_book_author);
        EditText i_4 = findViewById(R.id.admin_add_book_stock_quantity);
        EditText i_5 = findViewById(R.id.admin_add_book_category);
        EditText i_6 = findViewById(R.id.admin_add_book_type);
        EditText i_7 = findViewById(R.id.admin_add_book_price);
        EditText i_8 = findViewById(R.id.admin_add_book_ozet);



        Button updateButton = findViewById(R.id.add_book_ekle_button);
        updateButton.setText("Kitap bilgilerini guncelle");
    }
}
