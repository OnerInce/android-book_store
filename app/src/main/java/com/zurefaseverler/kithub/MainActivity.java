package com.zurefaseverler.kithub;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

/*  Toast.makeText(context, "test toast message",Toast.LENGTH_SHORT).show();   */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{


    private static int Splash_time_out = 5000;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    Context context = this;



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sepet:
                Intent i = new Intent(this, Sepet.class);
                startActivity(i);
                break;
            case R.id.profil:
                i = new Intent(this,login.class );
                startActivity(i);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {

            drawer.closeDrawer(GravityCompat.END);

        } else {

            finishAffinity();
            System.exit(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*  drawer  */
        drawer = findViewById(R.id.drawer_layout);
        toggle= new ActionBarDrawerToggle(this, drawer, R.string.open_drawer,R.string.close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        ImageButton button_drawer = findViewById(R.id.button_drawer);
        button_drawer.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_drawer:
                drawer.openDrawer(GravityCompat.END);
                break;


        }

    }

}
