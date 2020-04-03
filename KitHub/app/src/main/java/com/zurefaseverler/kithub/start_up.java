package com.zurefaseverler.kithub;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class start_up extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);
        Thread timeTread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(start_up.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };
        timeTread.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
