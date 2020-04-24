package com.zurefaseverler.kithub;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCategory extends AppCompatActivity {

    private String category;
    private String book_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        final String s = "";
        final EditText category_edit = findViewById(R.id.admin_add_category_category);
        final EditText type_edit = findViewById(R.id.admin_add_category_book_type);

        Button add = findViewById(R.id.ekle);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = category_edit.getText().toString();
                book_type = type_edit.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(AddCategory.this);
                builder.setCancelable(true);

                if (category.matches("")) {
                    builder.setMessage(R.string.category_add_enter_name);
                    builder.setTitle(R.string.category_add_error);
                    builder.setNegativeButton(R.string.category_add_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }

                else
                    {
                    if (book_type.matches(""))
                        builder.setMessage(category + getString(R.string.category_add_sure));
                    else {
                        String confirm_text = getString(R.string.category_add_sure2, category, book_type);
                        builder.setMessage(confirm_text);
                    }
                    builder.setTitle(R.string.category_add_confirm);

                    builder.setNegativeButton(R.string.category_add_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton(R.string.category_add_verify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RequestQueue mQueue = Volley.newRequestQueue(AddCategory.this);
                            String url = "http://18.204.251.116/add_category.php";
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    }
                                    ){
                                @Override
                                protected Map<String, String> getParams(){
                                    if (!AdapterCategories.categories.contains(category))
                                        AdapterCategories.categories.add(category);

                                    Map<String,String> params = new HashMap<>();
                                    if(book_type != null){
                                        params.put("book_type_name", book_type);
                                        ArrayList<String> old_list = AdapterCategories.bookTypes.get(category);
                                        if (old_list != null){
                                            old_list.add(book_type);
                                            AdapterCategories.bookTypes.put(category, old_list);
                                        }
                                        else {
                                            ArrayList<String> new_list = new ArrayList<>();
                                            new_list.add(book_type);
                                            AdapterCategories.bookTypes.put(category, new_list);

                                        }
                                    }
                                    params.put("category_name",category);
                                    return params;
                                }
                            };
                            mQueue.add(request);
                            Toast.makeText(AddCategory.this, R.string.category_add_success,Toast.LENGTH_SHORT).show();
                        }
                    }
                );
                    }
                builder.show();
                category_edit.setText(s);
                type_edit.setText(s);
            }
        });

    }





}
