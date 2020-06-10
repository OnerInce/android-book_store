package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;

import static com.zurefaseverler.kithub.StartUp.HOST;

public class UserShowSearch extends AppCompatActivity {

    static class SearchResults {

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("e_mail")
        private String e_mail;
        @SerializedName("phone")
        private String phone;
        @SerializedName("address")
        private String address;
        @SerializedName("image")
        private String image;

        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getE_mail() {
            return e_mail;
        }
        public String getPhone() {
            return phone;
        }
        public String getAddress() {
            return address;
        }
        public String getImage() {
            return image;
        }

        public SearchResults(String id, String name, String e_mail, String phone, String address, String image) {
            this.id = id;
            this.name = name;
            this.e_mail = e_mail;
            this.phone = phone;
            this.address = address;
            this.image = image;
        }
    }

    interface MyAPIService {
        @FormUrlEncoded
        @POST("search_users.php")
        Call<List<SearchResults>> searchKitHub(@Field("query") String query);
    }

    static class RetrofitClientInstance {
        private static Retrofit retrofit;

        static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
    static class ListViewAdapter extends BaseAdapter {

        private List<SearchResults> users;
        private Context context;

        ListViewAdapter(Context context, List<SearchResults> users) {
            this.context = context;
            this.users = users;
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int pos) {
            return users.get(pos);
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

            final SearchResults thisUser = users.get(position);

            nameTxt.setText(thisUser.getName());
            txtAuthor.setText(thisUser.getE_mail());

            return view;
        }

    }

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private SearchView mSearchView;

    private void initializeWidgets(){
        mGridView = findViewById(R.id.mGridView);
        mProgressBar = findViewById(R.id.mProgressBar);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
        mSearchView = findViewById(R.id.mSearchView);
        mSearchView.setIconified(true);
    }

    private void populateListView(List<SearchResults> bookList) {
        ListViewAdapter adapter = new ListViewAdapter(this, bookList);
        mGridView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usershowsearch);

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
        mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();
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

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SearchResults user = (SearchResults) mGridView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                intent.putExtra("user_id", user.getId());
                intent.putExtra("name", user.getName());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("e_mail", user.getE_mail());
                intent.putExtra("address", user.getAddress());
                intent.putExtra("image", user.getImage());
                startActivity(intent);
            }
        });
    }
}

