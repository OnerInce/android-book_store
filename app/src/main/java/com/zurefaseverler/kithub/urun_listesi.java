package com.zurefaseverler.kithub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class urun_listesi extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urun_listesi);

        TextView yazi =(TextView)findViewById(R.id.kitap_turu);

        Intent i = getIntent();
        String bilgi = i.getStringExtra("mesaj");
        yazi.setText(bilgi);

    }


    public void gonder(View view) {
        Intent intent = new Intent(getApplicationContext(), urun_sayfasi.class);
        startActivity(intent);


    }
}
