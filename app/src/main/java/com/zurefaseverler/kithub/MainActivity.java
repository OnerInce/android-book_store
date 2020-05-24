package com.zurefaseverler.kithub;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    int loggedInId;
    int isAdmin;
    NavigationView navigationView;
    ImageButton searchButton;

    ArrayList<MainPageBook> mostDiscountList = new ArrayList<>();
    ArrayList<MainPageBook> newComersList = new ArrayList<>();
    ArrayList<MainPageBook> mostSellersList = new ArrayList<>();

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sepet:
                Intent i = new Intent(this, CartActivity.class);
                startActivity(i);
                break;

            case R.id.giris:
                i = new Intent(this, Login.class);
                startActivity(i);
                break;

            case R.id.profil:
                i = new Intent(this, ProfilePage.class );
                startActivity(i);
                break;

            case R.id.kategoriler:
                i = new Intent(this, Categories.class);
                startActivity(i);
                break;
                
            case R.id.admin_drawer:
                i = new Intent(this, Admin.class);
                startActivity(i);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        else {
            finishAffinity();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent toSearch = new Intent(this, Search.class);
        searchButton = findViewById(R.id.Main_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toSearch);
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loggedInId = sharedPref.getInt("id",-1);
        isAdmin = sharedPref.getInt("isAdmin",0);

        /*  drawer  */
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_drawer,R.string.close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton button_drawer = findViewById(R.id.button_drawer);
        button_drawer.setOnClickListener(this);

        if (loggedInId == -1) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu_guest);
        }
        else if (isAdmin == 1) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu_admin);

        }
        else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu_user);
        }

        doldur(new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                LinearLayoutManager layoutManageronecikanlar = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView LayoutOneCikanlar = findViewById(R.id.main_page_best_discount);
                LayoutOneCikanlar.setLayoutManager(layoutManageronecikanlar);
                MainPageRecyclerViewAdapter adapteronecikanlar = new MainPageRecyclerViewAdapter(MainActivity.this, mostDiscountList);
                LayoutOneCikanlar.setAdapter(adapteronecikanlar);

                LinearLayoutManager layoutManagercoksatanlar = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView Layoutcoksatanlar = findViewById(R.id.main_page_best_seller);
                Layoutcoksatanlar.setLayoutManager(layoutManagercoksatanlar);
                MainPageRecyclerViewAdapter adaptercoksatanlar = new MainPageRecyclerViewAdapter(MainActivity.this, mostSellersList);
                Layoutcoksatanlar.setAdapter(adaptercoksatanlar);

                LinearLayoutManager layoutManageryeniurunler = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerView Layoutyeniurunler = findViewById(R.id.main_page_new_books);
                Layoutyeniurunler.setLayoutManager(layoutManageryeniurunler);
                MainPageRecyclerViewAdapter adapteryeniurunler = new MainPageRecyclerViewAdapter(MainActivity.this, newComersList);
                Layoutyeniurunler.setAdapter(adapteryeniurunler);

            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loggedInId = sharedPref.getInt("id",-1);
        isAdmin = sharedPref.getInt("isAdmin",0);

        /*  drawer  */
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_drawer,R.string.close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton button_drawer = findViewById(R.id.button_drawer);
        button_drawer.setOnClickListener(this);

        Button test = findViewById(R.id.one_cikanlar);
        test.setOnClickListener(this);

        if (loggedInId == -1) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu_guest);
        }
        else if (isAdmin == 1) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu_admin);

        }
        else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_menu_user);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_drawer:
                drawer.openDrawer(GravityCompat.END);
                break;

        }

    }

    public void doldur(final VolleyResponseListener listener){
        String url = "http://18.204.251.116/main_page_books.php";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){

                                JSONObject book = jsonArray.getJSONObject(i);
                                String author_name = book.getString("first_name") + " " + book.getString("last_name");
                                MainPageBook item = new MainPageBook(book.getString("image"),book.getString("title"), author_name, "%20",book.getString("price"));
                                if(i < 5)
                                    mostDiscountList.add(item);
                                else if (i < 10)
                                    mostSellersList.add(item);
                                else
                                    newComersList.add(item);
                            }
                            listener.onResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                );
        NetworkRequests.getInstance(this).addToRequestQueue(request);
    }
}
