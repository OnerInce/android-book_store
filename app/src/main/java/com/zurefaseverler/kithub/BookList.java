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

import java.util.ArrayList;
import java.util.List;

public class BookList extends Activity implements View.OnClickListener{

    List<MainPageBook> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        TextView title = findViewById(R.id.book_list_title);
        ImageButton goback = findViewById(R.id.go_back);
        goback.setOnClickListener(this);


        fill();
        RecyclerView view = findViewById(R.id.book_list_recycler_view);
        MainPageRecyclerViewAdapter adapter = new MainPageRecyclerViewAdapter(this,list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);

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
                //                                                                                  get data request atılacak bir adamın videosu üzerinden yaptım aynı fonksiypn buraya uyarlanacak
                //                                                                                  video linki :    https://www.youtube.com/watch?v=hJZClhRzjAo ardından https://www.youtube.com/watch?v=hFkFBjS7-vQ
                //                                                                                  ilk video req kısmı ve getData() adındaki fonksiyon ikinci kısmı biraz uyguladım zaten sadece yazılan getData() fonksiyonu burada çağırılacak

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

    public void fill(){

        for(int i = 0; i<20 ; i++){

            MainPageBook item = new MainPageBook("123","adem","adem","%20","50"+" TL");
            list.add(item);

        }
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
