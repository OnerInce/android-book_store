package com.zurefaseverler.kithub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

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
        radioGroupTaksit = findViewById(R.id.taksitGroup);
        radioGroupShipping = findViewById(R.id.shippingGroup);

        CardForm cardForm = findViewById(R.id.cardForm);

        TextView txtDes = findViewById(R.id.payment_amount);
        TextView txtPayment = findViewById(R.id.payment_amount_holder);
        Button btnPay = findViewById(R.id.btn_pay);
        EditText expiryDate = findViewById(R.id.expiry_date);
        EditText cardNumber = findViewById(R.id.card_number);
        EditText cardName = findViewById(R.id.card_name);

        expiryDate.setHint("Son Kullanma Tarihi");
        txtDes.setText("100TL");
        txtPayment.setText("Toplam Ödeme");
        cardNumber.setHint("Kart Numarası");
        cardName.setHint("Kart Sahibi");





        btnPay.setText(String.format("Toplam: %s",txtDes.getText()));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(Payment.this, "İsim" + card.getName() +"| Son 4 Hane: " + card.getLast4(), Toast.LENGTH_SHORT).show();
            }
        });




    }





}
