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

public class DeleteCategory extends AppCompatActivity {

    private int son_Expand_group = -1;
    private String category;
    private String book_type;
    private String temp;
    final String sep = "  /  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);
        final Button deleteButton = findViewById(R.id.delete_button);
        final ExpandableListView categories = findViewById(R.id.acilirKategoriler);
        final TextView sil_text = findViewById(R.id.sil_tur);

        categories.setAdapter(new AdapterCategories(this, AdapterCategories.categories, AdapterCategories.bookTypes));

        if(AdapterCategories.categories.size() == 0) {
            deleteButton.setVisibility(View.INVISIBLE);
            sil_text.setText(R.string.category_delete_not_found);
        }

        categories.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(son_Expand_group != -1 && groupPosition != son_Expand_group){
                    categories.collapseGroup(son_Expand_group);
                }
                son_Expand_group = groupPosition;
            }
        });

        categories.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                category = ((TextView) v).getText().toString();
                temp = category;
                sil_text.setText(category);
                book_type = null;

                return false;
            }
        });

        categories.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                book_type = ((TextView) v).getText().toString();
                temp = category + sep + book_type;
                sil_text.setText(temp);

                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCategory.this);
                builder.setCancelable(true);
                builder.setMessage(temp + getString(R.string.category_delete_sure));
                builder.setTitle(R.string.category_delete_confirm);

                builder.setNegativeButton(R.string.category_delete_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton(R.string.category_delete_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue mQueue = Volley.newRequestQueue(DeleteCategory.this);
                        String url = "http://18.204.251.116/delete_category.php";
                        StringRequest request = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if(book_type == null){
                                                AdapterCategories.bookTypes.remove(category);
                                                AdapterCategories.categories.remove(AdapterCategories.categories.indexOf(category));
                                            }
                                            else{
                                                AdapterCategories.bookTypes.get(category).remove(AdapterCategories.bookTypes.get(category).indexOf(book_type));
                                            }
                                            categories.setAdapter( new AdapterCategories(DeleteCategory.this, AdapterCategories.categories, AdapterCategories.bookTypes));
                                            if(AdapterCategories.categories.size() == 0) {
                                                deleteButton.setVisibility(View.INVISIBLE);
                                                sil_text.setText(R.string.category_delete_not_found);
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
                            protected Map<String, String> getParams(){
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
