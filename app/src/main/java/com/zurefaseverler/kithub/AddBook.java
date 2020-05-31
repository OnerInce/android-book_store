package com.zurefaseverler.kithub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class AddBook extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bitmap;
    private boolean imageAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final EditText i_1 = findViewById(R.id.ISBN);
        final EditText i_2 = findViewById(R.id.admin_add_book_title);
        final EditText i_3 = findViewById(R.id.admin_add_book_author);
        final EditText i_4 = findViewById(R.id.admin_add_book_stock_quantity);
        final EditText i_5 = findViewById(R.id.admin_add_book_category);
        final EditText i_6 = findViewById(R.id.admin_add_book_type);
        final EditText i_7 = findViewById(R.id.admin_add_book_price);
        final EditText i_8 = findViewById(R.id.admin_add_book_ozet);

        findViewById(R.id.addBookImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            }
        });

        Button addButton = findViewById(R.id.add_book_ekle_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ISBN = i_1.getText().toString();
                final String book_name = i_2.getText().toString();
                final String author = i_3.getText().toString();
                final String quantity = i_4.getText().toString();
                final String category = i_5.getText().toString();
                final String type = i_6.getText().toString();
                final String price = i_7.getText().toString();
                final String summary = i_8.getText().toString();

                if(ISBN.equals("") | book_name.equals("") | author.equals("") | quantity.equals("")
                        | category.equals("") | type.equals("") | price.equals("") |
                        summary.equals("") |!imageAdded){

                    Toast.makeText(AddBook.this, R.string.book_add_error,Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddBook.this);
                builder.setCancelable(true);
                builder.setMessage(book_name +getString(R.string.book_add_sure));
                builder.setTitle(R.string.book_add_confirm);

                builder.setNegativeButton(R.string.book_add_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton(R.string.book_add_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // database operations here
                        String url = "http://18.204.251.116/add_book.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String success = jsonObject.getString("success");
                                            if(success.equals("-1"))
                                                Toast.makeText(getApplicationContext(), R.string.add_book_already, Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(AddBook.this, R.string.book_add_success,Toast.LENGTH_SHORT).show();
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
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                String[] authorName = author.split(" ");
                                StringBuilder first_name = new StringBuilder();
                                for(int i=0; i<authorName.length-1; i++){
                                    first_name.append(authorName[i]);
                                }
                                params.put("ISBN",ISBN);
                                params.put("title",book_name);
                                params.put("first_name", first_name.toString());
                                params.put("last_name", authorName[authorName.length-1]);
                                params.put("stock", quantity);
                                params.put("category_name",category);
                                params.put("book_type_name",type);
                                params.put("price",price);
                                params.put("summary", summary);
                                params.put("image", imageToString(bitmap));
                                return params;
                            }
                        };
                        NetworkRequests.getInstance(AddBook.this).addToRequestQueue(stringRequest);
                    }
                });
                builder.show();

            }
        });
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
                imageAdded = true;
                //Picasso.with(MainActivity.this).load(selectedImageURI).noPlaceholder().centerCrop().fit()
                //      .into((ImageView) findViewById(R.id.image_display));
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
