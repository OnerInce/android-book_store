package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;

public class DiscountBookSearch extends AppCompatActivity {
    private static final String BASE_URL = "http://18.204.251.116";
    String bookName;

    static class SearchResults {

        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("first_name")
        private String first_name;
        @SerializedName("last_name")
        private String last_name;
        @SerializedName("price")
        private String price;


        @SerializedName("discount_value")
        private String discount;

        public SearchResults(String id, String title, String first_name, String last_name,
                             String price, String discount) {
            this.id = id;
            this.title = title;
            this.first_name = first_name;
            this.last_name = last_name;
            this.price = price;
            this.discount = discount;
        }

        public String getId() {
            return id;
        }
        public String getTitle() {
            return title;
        }
        String getFirst_name() {
            return first_name;
        }
        String getLast_name() {
            return last_name;
        }
        public String getPrice() {
            return price;
        }
        public String getDiscount() {
            return discount;
        }
        public void setDiscount(String discount) {
            this.discount = discount;
        }

        @NonNull
        @Override
        public String toString() {
            return getTitle();
        }
    }

    interface MyAPIService {
        @FormUrlEncoded
        @POST("search_books.php")
        Call<List<SearchResults>> searchKitHub(@Field("query") String query);
    }

    static class RetrofitClientInstance {
        private static Retrofit retrofit;

        static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
    static class ListViewAdapter extends BaseAdapter {

        private List<SearchResults> books;
        private Context context;

        ListViewAdapter(Context context, List<SearchResults> books) {
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
                view = LayoutInflater.from(context).inflate(R.layout.discount_search_model,
                        viewGroup, false);
            }

            TextView nameTxt = view.findViewById(R.id.nameTextView);
            TextView txtAuthor = view.findViewById(R.id.authorTextView);
            TextView quantity = view.findViewById(R.id.discount_quantity);

            final SearchResults thisBook = books.get(position);

            nameTxt.setText(thisBook.getTitle());
            txtAuthor.setText(thisBook.getFirst_name() + " " + thisBook.getLast_name());

            if (thisBook.getDiscount() == null)
                quantity.setText("İndirim miktarı: %0");
            else
                quantity.setText("İndirim miktarı: %" + thisBook.getDiscount());
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
        setContentView(R.layout.discount_publisher);

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
                final SearchResults book = (SearchResults) mGridView.getItemAtPosition(position);
                bookName = book.getTitle();
                final String bookID = book.getId();
                final TextView discount_tv = view.findViewById(R.id.discount_quantity);
                AlertDialog.Builder builder = new AlertDialog.Builder(DiscountBookSearch.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_discount_info,null);

                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                String dialogTitleStr = bookName + " " + getString(R.string.discount_sure);
                dialogTitle.setText(dialogTitleStr);

                builder.setCancelable(false);
                builder.setView(dialogView);

                Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
                Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);

                final EditText discount_amount_et = (EditText) dialogView.findViewById(R.id.discount_amount_et);
                final AlertDialog dialog = builder.create();

                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        String discount = discount_amount_et.getText().toString();
                        float discountAmount = Float.parseFloat(discount);
                        discountToDatabase(bookID, discountAmount);
                        discount_tv.setText("İndirim miktarı: %" + discount);
                        book.setDiscount(discount);

                    }
                });

                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplication(),
                                R.string.discount_cancel, Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });
    }

    private void discountToDatabase(final String book, final float amount) {
        String url = "http://18.204.251.116/discount.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(getApplication(),
                                        bookName + " adlı kitaba % " + amount + " indirim uyguladınız.", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getApplication(),
                                        bookName + " adlı kitabın indrimi güncellendi ", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", book);
                params.put("amount", Float.toString(amount));
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }
}

