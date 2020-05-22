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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    int loggedInId;
    int isAdmin;
    NavigationView navigationView;

    ArrayList<MainPageBook> list = new ArrayList<>();

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

            case R.id.siparisler:
                i = new Intent(this, ProfilePage.class );
                startActivity(i);
                break;

            case R.id.admin_drawer:
                i = new Intent(this, Admin.class);
                startActivity(i);
                break;

            case  R.id.yazarlar:
                i = new Intent(this, Authors.class );
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

        doldur();
        LinearLayoutManager layoutManageronecikanlar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView LayoutOneCikanlar = findViewById(R.id.main_page_best_discount);
        LayoutOneCikanlar.setLayoutManager(layoutManageronecikanlar);
        MainPageRecyclerViewAdapter adapteronecikanlar = new MainPageRecyclerViewAdapter(this,list);
        LayoutOneCikanlar.setAdapter(adapteronecikanlar);

        LinearLayoutManager layoutManagercoksatanlar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView Layoutcoksatanlar = findViewById(R.id.main_page_best_seller);
        Layoutcoksatanlar.setLayoutManager(layoutManagercoksatanlar);
        MainPageRecyclerViewAdapter adaptercoksatanlar = new MainPageRecyclerViewAdapter(this,list);
        Layoutcoksatanlar.setAdapter(adaptercoksatanlar);

        LinearLayoutManager layoutManageryeniurunler = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView Layoutyeniurunler = findViewById(R.id.main_page_new_books);
        Layoutyeniurunler.setLayoutManager(layoutManageryeniurunler);
        MainPageRecyclerViewAdapter adapteryeniurunler = new MainPageRecyclerViewAdapter(this,list);
        Layoutyeniurunler.setAdapter(adapteryeniurunler);
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

    public void doldur(){
        int i;

        for(i = 0; i < 5 ; i++ ){

            MainPageBook item = new MainPageBook("123","adem","adem","%20","50");
            list.add(item);

        }
    }

}
