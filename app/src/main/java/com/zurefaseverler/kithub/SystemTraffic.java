package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemTraffic extends AppCompatActivity{

    private String operation_type;
    private String time_filter;

    private Spinner op , time;
    public List<LogObj> list = new ArrayList<>();

    String log_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_traffic);

        op = findViewById(R.id.operation_type);
        time = findViewById(R.id.date_filter);
        Button apply = findViewById(R.id.filter_apply);

        ArrayAdapter<CharSequence> op_type_adapter = ArrayAdapter.createFromResource(this,R.array.operation_type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> time_filter_adapter = ArrayAdapter.createFromResource(this,R.array.time_filter, android.R.layout.simple_spinner_item);

        op_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_filter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        op.setAdapter(op_type_adapter);
        time.setAdapter(time_filter_adapter);



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_type = op.getSelectedItem().toString();
                time_filter = time.getSelectedItem().toString();
                Toast.makeText(SystemTraffic.this,operation_type+" "+time_filter, Toast.LENGTH_LONG).show();
                log_type = operation_type;
                fill(operation_type, time_filter, new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        RecyclerView recyclerView = findViewById(R.id.system_traffic_recyclerview);
                        final LogAdapter adapter = new LogAdapter(SystemTraffic.this, list);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SystemTraffic.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }

    public void fill(final String operation_type, final String time_filter, final VolleyResponseListener volleyResponseListener){
        String url = "http://18.204.251.116/get_log_record.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String actionTime = jsonObject.getString("action_time");
                                if(operation_type.equals("Giriş yapma") || operation_type.equals("Çıkış Yapma")
                                        || operation_type.equals("Kaydolma") || operation_type.equals("Kullanıcı bilgileri güncelleme")){
                                    String customer_id = jsonObject.getString("customer_id");
                                    String e_mail = jsonObject.getString("e_mail");
                                    String complete_name = jsonObject.getString("complete_name");
                                    String phone = jsonObject.getString("phone");
                                    LogObj temp = new LogObj(operation_type, customer_id, complete_name, e_mail,
                                            phone, null, actionTime, null, null, null,
                                            null, null, null, null,
                                            null);
                                    list.add(temp);
                                }
                                else if(operation_type.equals("Sepet İşlemleri")){
                                    String customer_id = jsonObject.getString("customer_id");
                                    String title = jsonObject.getString("title");
                                    String complete_name = jsonObject.getString("complete_name");
                                    String event_name = jsonObject.getString("event_name");
                                    LogObj temp = new LogObj(operation_type, customer_id, complete_name, null,
                                            null, null, actionTime, event_name, title, null,
                                            null, null, null, null,
                                            null);
                                    list.add(temp);
                                }
                                else if(operation_type.equals("Sipariş verme")){
                                    String order_id = jsonObject.getString("order_id");
                                    String company_name = jsonObject.getString("company_name");
                                    String complete_name = jsonObject.getString("complete_name");
                                    String total_price = jsonObject.getString("total_price");
                                    String nof_books = jsonObject.getString("nof_books");
                                    LogObj temp = new LogObj(operation_type, null, complete_name, null,
                                            null, null, actionTime, null, null, total_price,
                                            nof_books, order_id, null, null,
                                            null);
                                    list.add(temp);
                                }
                                else if(operation_type.equals("Yorum yapma")){
                                    String review_id = jsonObject.getString("review_id");
                                    String title = jsonObject.getString("title");
                                    String complete_name = jsonObject.getString("complete_name");
                                    String review_text = jsonObject.getString("review_text");
                                    String rating = jsonObject.getString("rating");
                                    LogObj temp = new LogObj(operation_type, null, complete_name, null,
                                            null, null, actionTime, null, title, null,
                                            null, null, review_id, rating,
                                            review_text);
                                    list.add(temp);
                                }

                            }
                            volleyResponseListener.onResponse(response);
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
                params.put("time", time_filter);
                params.put("operation", operation_type);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }


}
