package com.zurefaseverler.kithub;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class BookPage extends AppCompatActivity implements View.OnClickListener {

    private TextView summary;
    private boolean boolSummary, boolAuthor;
    private float initialRate;
    RatingBar ratingBar;
    private Book book;
    private CommentObj comment;
    private Button addCart, makeComment;
    private String book_id, name;
    List<CommentObj> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        Intent intent = getIntent();
        book_id = intent.getStringExtra("book_id");

        getBookInfo(book_id);

        ImageButton go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        TextView userName = findViewById(R.id.users_name);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name = sharedPref.getString("name", "");
        userName.setText(name);

        summary = findViewById(R.id.bookPage_summary);

        Button buttonSummary = findViewById(R.id.urun_sayfasi_ozet_button);
        buttonSummary.setOnClickListener(this);

        addCart = findViewById(R.id.bookPage_addCartButton);
        addCart.setOnClickListener(this);

        makeComment = findViewById(R.id.comment_send);
        makeComment.setOnClickListener(this);

    }

    private void getBookInfo(final String id) {
        String url = "http://18.204.251.116/books.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectInfo = jsonObject.getJSONObject("book_info");
                            JSONArray jsonArrayComment = jsonObject.getJSONArray("comments");

                            try {

                                book = new Book(jsonObjectInfo.getInt("id"),
                                        jsonObjectInfo.getString("first_name") + " " + jsonObjectInfo.getString("last_name"),
                                        jsonObjectInfo.getInt("stock_quantity"), jsonObjectInfo.getString("category_name"),
                                        jsonObjectInfo.getString("book_type_name"), jsonObjectInfo.getInt("price"),
                                        jsonObjectInfo.getInt("sales"), jsonObjectInfo.getInt("no_people_rated"),
                                        jsonObjectInfo.getInt("rate"), jsonObjectInfo.getString("ISBN"),
                                        jsonObjectInfo.getString("title"), jsonObjectInfo.getString("summary"),
                                        jsonObjectInfo.getString("image"));
                            } catch (JSONException e) {
                                book = new Book(jsonObjectInfo.getInt("id"),
                                        jsonObjectInfo.getString("first_name") + " " + jsonObjectInfo.getString("last_name"),
                                        jsonObjectInfo.getInt("stock_quantity"), jsonObjectInfo.getString("category_name"),
                                        jsonObjectInfo.getString("book_type_name"), jsonObjectInfo.getInt("price"),
                                        jsonObjectInfo.getInt("sales"), jsonObjectInfo.getInt("no_people_rated"),
                                        0, jsonObjectInfo.getString("ISBN"),
                                        jsonObjectInfo.getString("title"), jsonObjectInfo.getString("summary"),
                                        jsonObjectInfo.getString("image"));
                            }

                            for (int i = 0; i < jsonArrayComment.length(); i++){

                                comment = new CommentObj(jsonArrayComment.getJSONObject(i).getString("complete_name"), jsonArrayComment.getJSONObject(i).getString("review_text"),
                                        jsonArrayComment.getJSONObject(i).getString("review_date").split(" ")[0], jsonArrayComment.getJSONObject(i).getInt("rating"));
                                commentList.add(comment);
                            }

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
        ratingBar = findViewById(R.id.bookPage_ratingBar);
        initialRate = ratingBar.getNumStars();
        TextView price = findViewById(R.id.bookPage_price);
        addCart = findViewById(R.id.bookPage_addCartButton);

        updateComments(-1);

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

    private void updateComments(int newRate) {

        RecyclerView view = findViewById(R.id.comment_recycler_view);
        CommentDetailsAdapter adapter = new CommentDetailsAdapter(this, commentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);
        if(newRate != -1)
            ratingBar.setRating(((book.getRating() + newRate) / (book.getRatedCount() + 1)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back:
                onBackPressed();
                break;

            case R.id.bookPage_addCartButton:
                if(book.getStockQuantity() == 0) Toast.makeText(getApplicationContext(),"stokta yok",Toast.LENGTH_SHORT).show();
                else addItem_intoCart(book.getBook_id(), new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        final ImageView done = findViewById(R.id.bookPage_done);
                        final ImageView doneCircle = findViewById(R.id.bookPage_doneCircle);
                        Drawable drawable = done.getDrawable();
                        if(drawable instanceof AnimatedVectorDrawableCompat){
                            AnimatedVectorDrawableCompat drawableCompat = (AnimatedVectorDrawableCompat) drawable;
                            drawableCompat.start();
                        }else if(drawable instanceof AnimatedVectorDrawable){
                            AnimatedVectorDrawable vectorDrawable = (AnimatedVectorDrawable) drawable;
                            vectorDrawable.start();
                        }
                        doneCircle.bringToFront();
                        done.bringToFront();

                        done.setVisibility(View.VISIBLE);
                        doneCircle.setVisibility(View.VISIBLE);
                        doneCircle.postDelayed(new Runnable() {
                            public void run() {
                                done.setVisibility(View.GONE);
                                doneCircle.setVisibility(View.GONE);
                            }
                        }, 1500);
                    }
                });
                break;

            case R.id.urun_sayfasi_ozet_button:
                boolSummary = !boolSummary;
                summary.setVisibility(boolSummary ? View.VISIBLE: View.GONE);
                break;

            case R.id.comment_send:
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int id = sharedPref.getInt("id", -1);

                if(id == -1){
                    Toast.makeText(getApplicationContext(), R.string.bookpage_comment_login, Toast.LENGTH_SHORT).show();
                    break;
                }

                RatingBar commentRatingBar = findViewById(R.id.users_book_rate);
                int usersRating = (int) commentRatingBar.getRating();
                EditText comment = findViewById(R.id.users_comment);
                String commentContext = comment.getText().toString();

                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dateobj = new Date();

                CommentObj commentObj = new CommentObj(name, commentContext,
                        df.format(dateobj), usersRating);

                sendComment(usersRating, commentContext, commentObj);

                comment.setText("");
                break;
        }

    }

    private void sendComment(final int rate, final String comment, final CommentObj obj) {
        String url = "http://18.204.251.116/add_comment.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(Integer.parseInt(success) == 1) {
                                Toast.makeText(getApplicationContext(), R.string.bookpage_comment_success, Toast.LENGTH_SHORT).show();
                                commentList.add(obj);
                                updateComments(rate);
                            }
                            else
                                Toast.makeText(getApplicationContext(), R.string.bookpage_comment_exists, Toast.LENGTH_SHORT).show();
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
                params.put("book_id", book_id);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id",-1));
                params.put("customer_id", customer_id);
                params.put("rate", Integer.toString(rate));
                params.put("comment", comment);

                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void addItem_intoCart(final int book_id, final VolleyResponseListener listener) {
        String url = "http://18.204.251.116/add_to_cart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(Integer.parseInt(success) >= 0){
                                listener.onResponse(success);
                            }

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
                params.put("book_id", Integer.toString(book_id));

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String customer_id = Integer.toString(sharedPref.getInt("id",-1));
                params.put("customer_id",customer_id);

                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}
