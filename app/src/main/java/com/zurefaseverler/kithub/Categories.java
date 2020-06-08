package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import java.util.ArrayList;
import java.util.Objects;



public class Categories extends AppCompatActivity implements View.OnClickListener{

    private int lastExpandGroup = -1;
    private String category;
    ArrayList<String> book_types;
    int num_of_book_type;

    private RequestQueue mQueue;
    private ExpandableListView categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        categories = findViewById(R.id.acilirKategoriler);
        AdapterCategories a = new AdapterCategories(this, AdapterCategories.categories, AdapterCategories.bookTypes);
        categories.setAdapter(a);

        categories.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandGroup != -1 && groupPosition != lastExpandGroup){
                    categories.collapseGroup(lastExpandGroup);
                }
                lastExpandGroup = groupPosition;
            }
        });

        categories.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                category = ((TextView) v).getText().toString();
                book_types = AdapterCategories.bookTypes.get(category);

                num_of_book_type = book_types.size();
                if(num_of_book_type == 0){
                    Intent i = new Intent(getApplicationContext(), BookList.class);
                    startActivity(i);
                }


                return false;
            }
        });


        categories.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent i = new Intent(getApplicationContext(), BookList.class);
                String category_name = AdapterCategories.categories.get(groupPosition);
                String book_type = Objects.requireNonNull(AdapterCategories.bookTypes.get(category_name)).get(childPosition);
                i.putExtra("book_type", book_type);
                i.putExtra("category_name",category_name);
                startActivity(i);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.go_back) {
            onBackPressed();
        }
    }
}
