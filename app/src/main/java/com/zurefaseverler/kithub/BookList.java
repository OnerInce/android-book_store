package com.zurefaseverler.kithub;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface VolleyBookListInterface {
    void onResponse(List<MainPageBook> list);
}

public class BookList extends Activity implements View.OnClickListener{

    List<MainPageBook> list;
    String category_name;
    String book_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        category_name = getIntent().getStringExtra("category_name");
        book_type = getIntent().getStringExtra("book_type");

        TextView title = findViewById(R.id.book_list_title);
        title.setText(String.format("%s / %s", category_name, book_type));

        ImageButton goback = findViewById(R.id.go_back);
        goback.setOnClickListener(this);

        final RecyclerView view = findViewById(R.id.book_list_recycler_view);
        list = new ArrayList<>();
        fill(category_name, book_type, new VolleyBookListInterface() {
            @Override
            public void onResponse(List<MainPageBook> list) {
                MainPageRecyclerViewAdapter adapter = new MainPageRecyclerViewAdapter(BookList.this,list);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(BookList.this,2);
                view.setLayoutManager(layoutManager);
                view.setAdapter(adapter);
                findViewById(R.id.booklist_progressbar).setVisibility(View.GONE);
            }
        });


        view.addOnScrollListener(scrollListener);

    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(islastItemDispalying(recyclerView)){
                Log.i("Listeden geliyor","daha fazla yükle");
                //get data request atılacak bir adamın videosu üzerinden yaptım aynı fonksiypn buraya uyarlanacak
                //video linki :    https://www.youtube.com/watch?v=hJZClhRzjAo ardından https://www.youtube.com/watch?v=hFkFBjS7-vQ
                //ilk video req kısmı ve getData() adındaki fonksiyon ikinci kısmı biraz uyguladım zaten sadece yazılan getData() fonksiyonu burada çağırılacak

            }
        }
    };

    private boolean islastItemDispalying(RecyclerView recyclerView){

        if(recyclerView.getAdapter().getItemCount() != 0){

            int lastVisibleItemPos = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if(lastVisibleItemPos != RecyclerView.NO_POSITION && lastVisibleItemPos == recyclerView.getAdapter().getItemCount() - 1){
                return true;
            }
        }

     return false;
    }

    public void fill(final String category_name, final String book_type, final VolleyBookListInterface listener){
        //MainPageBook item = new MainPageBook("12", "http://18.204.251.116/var/www/html/images/book/4547546457456_9730.png","adem","adem","%20","50");
        //list.add(item);
        String url = "http://18.204.251.116/get_book_by_category.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject book = jsonArray.getJSONObject(i);
                                String author_name = book.getString("first_name") + " " + book.getString("last_name");
                                MainPageBook item = new MainPageBook(book.getString("id"), book.getString("image"),
                                        book.getString("title"), author_name, "%20", book.getString("price"));
                                list.add(item);
                            }
                            listener.onResponse(list);
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
                params.put("category_name", category_name);
                params.put("book_type_name", book_type);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                onBackPressed();
                break;
        }
    }

}
