package com.zurefaseverler.kithub;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookPage extends AppCompatActivity implements View.OnClickListener {

    ViewGroup InfoLayout;
    TextView summary;
    TextView author;
    Button buttonSummary;
    Button buttonAuthor;
    boolean boolSummary;
    boolean boolAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        InfoLayout = findViewById(R.id.urun_sayfasi_yazar_ozet_layout);
        author = findViewById(R.id.urun_sayfasi_yazar_txt);
        buttonSummary = findViewById(R.id.urun_sayfasi_ozet_button);
        buttonAuthor = findViewById(R.id.urun_sayfasi_yazar_button);
        buttonAuthor.setOnClickListener(this);
        buttonSummary.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.go_back:
                onBackPressed();
                break;

            case R.id.urun_sayfasi_ozet_button:

                boolSummary = !boolSummary;
                summary.setVisibility(boolSummary ? View.VISIBLE: View.GONE);

                break;
            case R.id.urun_sayfasi_yazar_button:

                boolAuthor = !boolAuthor;
                author.setVisibility(boolAuthor ? View.VISIBLE: View.GONE);

                break;
        }

    }
}
