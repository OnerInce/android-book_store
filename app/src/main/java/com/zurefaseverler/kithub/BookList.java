package com.zurefaseverler.kithub;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        ViewGroup parent_double_book = findViewById(R.id.parent_double_book);
        LayoutInflater inflater = LayoutInflater.from(this);

        for(int i = 0 ; i < 10 ; i++) {
            inflater.inflate(R.layout.double_book_view, parent_double_book);
        }

        TextView text = findViewById(R.id.kitap_turu);

        Intent i = getIntent();
        String info = i.getStringExtra("mesaj");
        text.setText(info);

    }
    public void gonder(View view) {

        Intent intent = new Intent(getApplicationContext(), BookPage.class);
        startActivity(intent);

    }
}
