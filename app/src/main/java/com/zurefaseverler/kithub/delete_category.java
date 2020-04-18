package com.zurefaseverler.kithub;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class delete_category extends AppCompatActivity {

    private int son_Expand_group = -1;
    private String category;            //üst kategori  (kitap kategorisi)
    private String book_type;           //alt kategori (kitap türü)
    private String temp;
    final String ara = "  /  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);
        final Button deleteButton = findViewById(R.id.delete_button);
        final ExpandableListView kategoriler = (ExpandableListView) findViewById(R.id.acilirKategoriler);
        final TextView sil_text = findViewById(R.id.sil_tur);

        kategoriler.setAdapter(new adapter_kategoriler(this,adapter_kategoriler.categories,adapter_kategoriler.bookTypes));

        if(adapter_kategoriler.categories.size() == 0) {
            deleteButton.setVisibility(View.INVISIBLE);
            sil_text.setText("Kategori Bulunamadı.");
        }

        kategoriler.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(son_Expand_group != -1 && groupPosition != son_Expand_group){

                    kategoriler.collapseGroup(son_Expand_group);


                }
                son_Expand_group = groupPosition;
            }
        });

        kategoriler.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                category = ((TextView) v).getText().toString();
                temp = category;
                sil_text.setText(category);
                book_type = null;
                return false;
            }
        });



        kategoriler.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                book_type = ((TextView) v).getText().toString();
                temp = category + ara + book_type;
                sil_text.setText(temp);


                return false;
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc",book_type+"  "+category);
                AlertDialog.Builder builder = new AlertDialog.Builder(delete_category.this);
                builder.setCancelable(true);
                builder.setMessage(temp+" kategorisini ya da kitap türünü silmek istediğinize emin misiniz?");
                builder.setTitle("SİLME ONAYI");

                builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] success = {"0","0","0"};
                        RequestQueue mQueue = Volley.newRequestQueue(delete_category.this);
                        String url = "http://18.204.251.116/delete_category.php";
                        StringRequest request = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            success[0] = jsonObject.getString("success");
                                            if(book_type == null){
                                                success[2] = category;
                                                adapter_kategoriler.bookTypes.remove(category);
                                                adapter_kategoriler.categories.remove(adapter_kategoriler.categories.indexOf(category));
                                            }
                                            else{
                                                success[1] = book_type;
                                                adapter_kategoriler.bookTypes.get(category).remove(adapter_kategoriler.bookTypes.get(category).indexOf(book_type));
                                            }
                                            kategoriler.setAdapter( new adapter_kategoriler(delete_category.this,adapter_kategoriler.categories,adapter_kategoriler.bookTypes));
                                            if(adapter_kategoriler.categories.size() == 0) {
                                                deleteButton.setVisibility(View.INVISIBLE);
                                                sil_text.setText("Kategori Bulunamadı.");
                                            }
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
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                if(book_type!=null){
                                    params.put("book_type_name",book_type);
                                }
                                params.put("category_name",category);
                                return params;
                            }
                        };
                        mQueue.add(request);
                    }
                });
                builder.show();
            }
        });


    }

}
