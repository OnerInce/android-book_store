package com.zurefaseverler.kithub;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookPage extends AppCompatActivity implements View.OnClickListener {

    private TextView summary;
    private boolean boolSummary, boolAuthor;

    private Book book;
    private Button addCart, makeComment, expandSummary;
    private RatingBar rating;
    List<CommentObj> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        Intent intent = getIntent();
        String id = intent.getStringExtra("book_id");

        getBookInfo(id);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        summary = findViewById(R.id.bookPage_summary);

        Button buttonSummary = findViewById(R.id.urun_sayfasi_ozet_button);
        buttonSummary.setOnClickListener(this);

        addCart = findViewById(R.id.bookPage_addCartButton);
        addCart.setOnClickListener(this);

        makeComment = findViewById(R.id.comment_send);
        makeComment.setOnClickListener(this);

        fill();
        RecyclerView view = findViewById(R.id.comment_recycler_view);
        CommentDetailsAdapter adapter = new CommentDetailsAdapter(this,commentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);

    }

    public void fill(){

        for(int i = 0 ; i < 2 ; i++){
            CommentObj temp = new CommentObj("user1", "sana puanim 3 kanka", "01.01.2020", 3);
            commentList.add(temp);
        }


    }



    private void getBookInfo(final String id) {
        String url = "http://18.204.251.116/books.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            book = new Book(jsonObject.getInt("id"),
                                    jsonObject.getString("first_name") + " " + jsonObject.getString("last_name"),
                                    jsonObject.getInt("stock_quantity"), jsonObject.getString("category_name"),
                                    jsonObject.getString("book_type_name"), jsonObject.getInt("price"),
                                    jsonObject.getInt("sales"), jsonObject.getInt("no_people_rated"),
                                    jsonObject.getInt("rating"), jsonObject.getString("ISBN"),
                                    jsonObject.getString("title"), jsonObject.getString("summary"),
                                    jsonObject.getString("image"));

                            setBookInfo(book);
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
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setBookInfo(Book book) {
        TextView bookName = findViewById(R.id.bookPage_bookName);
        ImageView bookImage = findViewById(R.id.bookPage_bookImage);
        TextView author_type = findViewById(R.id.bookPage_author_type);
        RatingBar ratingBar = findViewById(R.id.bookPage_ratingBar);
        TextView price = findViewById(R.id.bookPage_price);

        addCart = findViewById(R.id.bookPage_addCartButton);

        bookName.setText(book.getTitle());
        author_type.setText(String.format("%s / %s", book.getAuthor(), book.getBookType()));
        price.setText(String.format("%s TL",book.getPrice()));

        if(book.getRatedCount() != 0)
            ratingBar.setRating(book.getRating() / book.getRatedCount());
        else
            ratingBar.setRating(0);

        summary.setText(book.getSummary());

        String[] temp = book.getImage().split("html/");
        if(temp.length == 1){
            Picasso.get().load(book.getImage()).into(bookImage);
        }
        else{
            Picasso.get().load("http://18.204.251.116/" + temp[1]).into(bookImage);
        }

        if(book.getStockQuantity() == 0) addCart.setText(R.string.book_page_remindMe);
        else addCart.setText(R.string.book_page_addCart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back:
                onBackPressed();
                break;

            case R.id.bookPage_addCartButton:
                if(book.getStockQuantity() == 0) Toast.makeText(getApplicationContext(),"stokta yok",Toast.LENGTH_SHORT).show();
                else addItem_intoCart(book.getBook_id());
                break;

            case R.id.urun_sayfasi_ozet_button:
                boolSummary = !boolSummary;
                summary.setVisibility(boolSummary ? View.VISIBLE: View.GONE);
                break;

            case R.id.comment_send:
                TextView userName = findViewById(R.id.users_name); // name icin databaseden settext tarzi bisey yapilmasi lazim ama bunun burda degil gonder tusuna basilmadan once yapilmasi lazim
                String name = userName.getText().toString();
                EditText comment = findViewById(R.id.users_comment);
                String usersComment = comment.getText().toString();
                comment.setText("");
                RatingBar commentRatingBar = findViewById(R.id.users_book_rate);
                Float usersRating = commentRatingBar.getRating();
                commentRatingBar.setRating(0);
                System.out.println("name " +name);
                System.out.println("comment " +usersRating);
                System.out.println("rating " +usersRating);

                break;
        }

    }

    private void addItem_intoCart(final int book_id) {
        String url = "http://18.204.251.116/add_to_cart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(Integer.parseInt(success) >= 0)   Toast.makeText(getApplicationContext(),"eklendi",Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("book_id",Integer.toString(book_id));

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id",-1));
                params.put("customer_id",customer_id);

                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
