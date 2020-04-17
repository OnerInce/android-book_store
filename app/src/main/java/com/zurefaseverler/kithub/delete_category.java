package com.zurefaseverler.kithub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class delete_category extends AppCompatActivity {

    private int son_Expand_group = -1;
    private String category;            //üst kategori  (kitap kategorisi)
    private String book_type;           //alt kategori (kitap türü)
    private String temp;
    final String ara = "  /  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);

        final ExpandableListView kategoriler = (ExpandableListView) findViewById(R.id.acilirKategoriler);
        kategoriler.setAdapter( new adapter_kategoriler(this));

        final TextView sil_text = findViewById(R.id.sil_tur);

        kategoriler.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(son_Expand_group != -1 && groupPosition != son_Expand_group){

                    kategoriler.collapseGroup(son_Expand_group);


                }
                son_Expand_group = groupPosition;
            }
        });

        kategoriler.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                category = ((TextView) v).getText().toString();
                temp = category;
                sil_text.setText(category);

                return false;
            }
        });



        kategoriler.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                book_type = ((TextView) v).getText().toString();
                temp = category + ara + book_type;
                sil_text.setText(temp);


                return false;
            }
        });

        Button add = findViewById(R.id.delete_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(delete_category.this);
                builder.setCancelable(true);
                builder.setMessage(temp+" kategorisini ya da kitap türünü silmek istediğinize emin misiniz?");
                builder.setTitle("SİLME ONAYI");

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


                        Toast.makeText(delete_category.this, "Kategori başarıyla silindi",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

            }
        });


    }
}
