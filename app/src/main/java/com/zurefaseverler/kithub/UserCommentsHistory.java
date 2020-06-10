package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zurefaseverler.kithub.StartUp.HOST;

public class UserCommentsHistory extends AppCompatActivity {

    List<OldCommentsObj> list = new ArrayList<>();
    private OldCommentsObj comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comments_history);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = sharedPref.getInt("id", -1);

        getComments(id);
    }

    private void getComments(final int id) {
        String url = HOST + "user_comments.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String title = jsonArray.getJSONObject(i).getString("title");
                                String commentText = jsonArray.getJSONObject(i).getString("review_text");
                                String commentDate = jsonArray.getJSONObject(i).getString("review_date").split(" ")[0];
                                String rate = jsonArray.getJSONObject(i).getString("rating");

                                comment = new OldCommentsObj(title, commentText, commentDate, Integer.parseInt(rate));
                                list.add(comment);
                            }
                                RecyclerView view = findViewById(R.id.old_comments);
                                OldCommentAdapter adapter = new OldCommentAdapter(UserCommentsHistory.this, list);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserCommentsHistory.this);
                                view.setLayoutManager(layoutManager);
                                view.setAdapter(adapter);

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
                params.put("id", Integer.toString(id));
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

}
