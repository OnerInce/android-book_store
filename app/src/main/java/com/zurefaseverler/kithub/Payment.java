package com.zurefaseverler.kithub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity {

    RadioGroup radioGroupTaksit;
    RadioGroup radioGroupShipping;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        radioGroupTaksit=findViewById(R.id.taksitGroup);
        radioGroupShipping=findViewById(R.id.shippingGroup);



    }





}
