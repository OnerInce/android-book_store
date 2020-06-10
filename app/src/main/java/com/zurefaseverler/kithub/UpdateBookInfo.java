package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.zurefaseverler.kithub.StartUp.HOST;

public class UpdateBookInfo extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1 ;
    private Book book;
    private EditText ISBN, author, book_name, category, bookType, price, description;
    private boolean isImageChanged = false;
    private Bitmap bitmap;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        final String book_id = getIntent().getStringExtra("book_id");

        getBookInfo(book_id);

        TextView baslik = findViewById(R.id.baslik);
        baslik.setText("Kitap Güncelleme");

        Button updateButton = findViewById(R.id.add_book_ekle_button);
        updateButton.setText("Kitap bilgilerini guncelle");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ISBN.getText().toString().length() != 10)   Toast.makeText(UpdateBookInfo.this, "ISBN 10 haneli olmalıdır.", Toast.LENGTH_SHORT).show();
                else updateBookInfo(book_id);
            }
        });

        findViewById(R.id.addBookImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }
        });
    }

    private void updateBookInfo(final String book_id) {
        String url = HOST + "update_book.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            Toast.makeText(getApplicationContext(),success,Toast.LENGTH_SHORT).show();
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
                params.put("id", book_id);
                params.put("operation", "update_info");

                String[] authorName = author.getText().toString().split(" ");
                StringBuilder first_name = new StringBuilder();
                for(int i=0; i < authorName.length-1; i++){
                    first_name.append(authorName[i]).append(" ");
                }
                params.put("ISBN", ISBN.getText().toString());
                params.put("title", book_name.getText().toString());
                params.put("first_name", first_name.toString());
                params.put("last_name", authorName[authorName.length-1]);
                params.put("category_name", category.getText().toString());
                params.put("book_type_name", bookType.getText().toString());
                params.put("price", price.getText().toString());
                params.put("summary", description.getText().toString());
                if(isImageChanged) {
                    params.put("image_changed", "1");
                    params.put("image", imageToString(bitmap));
                }
                else {
                    params.put("image_changed", "0");
                    params.put("image", book.getImage());
                }
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getBookInfo(final String book_id) {
        String url = HOST + "update_book.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectInfo = jsonObject.getJSONObject("book_info");
                            book = new Book(jsonObjectInfo.getInt("id"),
                                    jsonObjectInfo.getString("first_name") + " " + jsonObjectInfo.getString("last_name"),
                                    0, jsonObjectInfo.getString("category_name"),
                                    jsonObjectInfo.getString("book_type_name"), jsonObjectInfo.getInt("price"),
                                    0, 0,
                                    0, jsonObjectInfo.getString("ISBN"),
                                    jsonObjectInfo.getString("title"), jsonObjectInfo.getString("summary"),
                                    jsonObjectInfo.getString("image"), 0);
                            setBookInfo();
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
                params.put("id", book_id);
                params.put("operation", "get_info");
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    private void setBookInfo() {
        EditText stock = (EditText) findViewById(R.id.admin_add_book_stock_quantity);
        TextView stockText = (TextView) findViewById(R.id.addBook_stockText);
        stock.setVisibility(View.GONE);
        stockText.setVisibility(View.GONE);

        ISBN = (EditText) findViewById(R.id.ISBN);
        book_name = (EditText) findViewById(R.id.admin_add_book_title);
        author = (EditText) findViewById(R.id.admin_add_book_author);

        category = (EditText) findViewById(R.id.admin_add_book_category);
        bookType = (EditText) findViewById(R.id.admin_add_book_type);
        price = (EditText) findViewById(R.id.admin_add_book_price);
        description = (EditText) findViewById(R.id.admin_add_book_ozet);
        image = (ImageView) findViewById(R.id.addBookImage);

        ISBN.setText(book.getISBN());
        book_name.setText(book.getTitle());
        author.setText(book.getAuthor());
        stock.setText(Integer.toString(book.getStockQuantity()));
        category.setText(book.getCategory());
        bookType.setText(book.getBookType());
        price.setText(Float.toString(book.getPrice()));
        description.setText(book.getSummary());

        String[] temp = book.getImage().split("html/");
        if(temp.length == 1){
            Picasso.get().load(book.getImage()).into(image);
        }
        else{
            Picasso.get().load(HOST + temp[1]).into(image);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                ImageView imageView = findViewById(R.id.addBookImage);
                Uri selectedImageURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Picasso.get().load(selectedImageURI).fit().into(imageView);
                isImageChanged = true;
            }
        }
    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
    }
}
