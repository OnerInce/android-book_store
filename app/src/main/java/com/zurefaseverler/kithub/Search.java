package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;

public class Search extends AppCompatActivity {
    private static final String BASE_URL = "http://18.204.251.116";

    class SearchResults {
        @SerializedName("title")
        private String title;
        @SerializedName("ISBN")
        private String ISBN;
        @SerializedName("first_name")
        private String first_name;
        @SerializedName("last_name")
        private String last_name;
        @SerializedName("image")
        private String image;
        @SerializedName("price")
        private String price;

        public SearchResults(String title, String ISBN, String first_name, String last_name,
                             String image, String price) {
            this.title = title;
            this.ISBN = ISBN;
            this.first_name = first_name;
            this.last_name = last_name;
            this.image = image;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public String getISBN() {
            return ISBN;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getImage() {
            return image;
        }

        public String getPrice() {
            return price;
        }
    }

    interface MyAPIService {
        @FormUrlEncoded
        @POST("search_books.php")
        Call<List<SearchResults>> searchKitHub(@Field("query") String query);
    }

    static class RetrofitClientInstance {
        private static Retrofit retrofit;

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
    class ListViewAdapter extends BaseAdapter {

        private List<SearchResults> books;
        private Context context;

        public ListViewAdapter(Context context, List<SearchResults> books) {
            this.context = context;
            this.books = books;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int pos) {
            return books.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.activity_search_model,
                        viewGroup, false);
            }

            TextView nameTxt = view.findViewById(R.id.nameTextView);
            TextView txtAuthor = view.findViewById(R.id.authorTextView);
            ImageView imageBook = view.findViewById(R.id.searchResultImageView);

            final SearchResults thisBook = books.get(position);

            nameTxt.setText(thisBook.getTitle());
            txtAuthor.setText(thisBook.getFirst_name() + " " + thisBook.getLast_name());

            /*if (thisBook.getImageURL() != null && thisBook.getImageURL().length() > 0) {
                Picasso.get().load(FULL_URL + "/images/" + thisBook.getImageURL()).placeholder(R.drawable.placeholder).into(spacecraftImageView);
            } else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.placeholder).into(spacecraftImageView);
            }*/

            return view;
        }

    }

    private ListView mListView;
    private ProgressBar mProgressBar;
    private SearchView mSearchView;

    private void initializeWidgets(){
        mListView = findViewById(R.id.mListView);
        mProgressBar = findViewById(R.id.mProgressBar);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
        mSearchView = findViewById(R.id.mSearchView);
        mSearchView.setIconified(true);
    }

    private void populateListView(List<SearchResults> spacecraftList) {
        ListViewAdapter adapter = new ListViewAdapter(this, spacecraftList);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.initializeWidgets();
        final MyAPIService myAPIService = RetrofitClientInstance.getRetrofitInstance().create(MyAPIService.class);

        final Call<List<SearchResults>> call = myAPIService.searchKitHub("empty result");
        call.enqueue(new Callback<List<SearchResults>>() {

            @Override
            public void onResponse(@NonNull Call<List<SearchResults>> call,
                                   @NonNull Response<List<SearchResults>> response) {
                mProgressBar.setVisibility(View.GONE);
                populateListView(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<List<SearchResults>> call,
                                  @NonNull Throwable throwable) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.equals("")){
                    final Call<List<SearchResults>> call = myAPIService.searchKitHub(query);
                    call.enqueue(new Callback<List<SearchResults>>() {

                        @Override
                        public void onResponse(@NonNull Call<List<SearchResults>> call,
                                               @NonNull Response<List<SearchResults>> response) {
                            mProgressBar.setVisibility(View.GONE);
                            populateListView(response.body());
                        }
                        @Override
                        public void onFailure(@NonNull Call<List<SearchResults>> call,
                                              @NonNull Throwable throwable) {
                            populateListView(new ArrayList<SearchResults>());
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                    return false;
            }
            return false;
            }
        });
    }
}

