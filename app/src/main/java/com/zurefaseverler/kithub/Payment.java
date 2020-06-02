package com.zurefaseverler.kithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

class RequestMember
{
    private String customerID;
    private String shipper;
    private String address;
    private String totalOrderPrice;
    private List<Order> orders;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getShipperID() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

public class Payment extends AppCompatActivity {

    RadioGroup radioGroupTaksit, radioGroupShipping;
    RadioButton selectedShipper, selectedInstall;
    Button finish;
    EditText addressText;
    int ITEMS_ON_INTENT = 4;
    static String CUSTOMER_ID, SHIPPER, TOTAL_PRICE, ADDRESS;
    String orderJSONRequest;
    JSONObject orderJSONObject;
    List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CUSTOMER_ID = Integer.toString(sharedPref.getInt("id", -1));

        setContentView(R.layout.activity_pay);

        CardForm cardForm = findViewById(R.id.cardForm);

        TextView txtDes = findViewById(R.id.payment_amount);
        TextView txtPayment = findViewById(R.id.payment_amount_holder);
        //Button btnPay = findViewById(R.id.btn_pay);
        EditText expiryDate = findViewById(R.id.expiry_date);
        EditText cardNumber = findViewById(R.id.card_number);
        EditText cardName = findViewById(R.id.card_name);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        TOTAL_PRICE = bundle.getString("TOTAL_ORDER_PRICE");

        expiryDate.setHint("Son Kullanma Tarihi");
        txtDes.setText(TOTAL_PRICE);
        txtPayment.setText("Toplam Ödeme");
        cardNumber.setHint("Kart Numarası");
        cardName.setHint("Kart Sahibi");
      
      
        finish = findViewById(R.id.btn_pay);
        finish.setText(String.format("Toplam: %s",TOTAL_PRICE));
        radioGroupTaksit = findViewById(R.id.taksitGroup);
        radioGroupShipping = findViewById(R.id.shippingGroup);
        addressText = (EditText) findViewById(R.id.address);
        getAddress(CUSTOMER_ID);

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                int selectedShippingId = radioGroupShipping.getCheckedRadioButtonId();
                int selectedInstallId = radioGroupTaksit.getCheckedRadioButtonId();
                selectedShipper = (RadioButton) findViewById(selectedShippingId);
                selectedInstall = (RadioButton) findViewById(selectedInstallId);
                if(selectedShipper == null){
                    Toast.makeText(getApplicationContext(), R.string.payment_select_shipper, Toast.LENGTH_LONG).show();
                    return;
                }
                if(selectedInstall == null){
                    Toast.makeText(getApplicationContext(), R.string.pay_select_install, Toast.LENGTH_LONG).show();
                    return;
                }
                SHIPPER = ((String) selectedShipper.getText()).split(" ")[0];
                ADDRESS = addressText.getText().toString();

                int nofItems = bundle.keySet().size() / ITEMS_ON_INTENT;

                for (int i = 0; i < nofItems; i++){
                    String cartItemID = "ITEM" + i;
                    String book_id = bundle.getString(cartItemID + " BOOK");
                    String quantity = bundle.getString(cartItemID + " QUANTITY");
                    String single_price = bundle.getString(cartItemID + " PRICE");

                    Order currentOrder = new Order(Integer.parseInt(book_id),
                            Integer.parseInt(quantity), Float.parseFloat(single_price));
                    orderList.add(currentOrder);
                }

                RequestMember mem = createjsonObject(orderList);
                Gson gson = new Gson();
                orderJSONRequest = gson.toJson(mem);
                try {
                    orderJSONObject = new JSONObject(orderJSONRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(orderJSONObject);
                orderToDatabase();

                Intent i = new Intent(Payment.this, PaymentCorrect.class);
                startActivity(i);
            }
        });
    }

    private static RequestMember createjsonObject(List<Order> orders)
    {
        RequestMember member= new RequestMember();
        member.setAddress(ADDRESS);
        member.setShipper(SHIPPER);
        member.setCustomerID(CUSTOMER_ID);
        member.setTotalOrderPrice(TOTAL_PRICE);

        member.setOrders(orders);
        return member;
    }

    private void orderToDatabase() {
        String url = "http://18.204.251.116/place_order.php";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, orderJSONObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
        };
        NetworkRequests.getInstance(this).addToRequestQueue(jsonRequest);
    }

    private void getAddress(final String id) {
        String url = "http://18.204.251.116/get_address.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String address = jsonObject.getString("address");
                            addressText.setText(address);
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
                params.put("id", id);
                return params;
            }
        };
        NetworkRequests.getInstance(this).addToRequestQueue(stringRequest);
    }

}

