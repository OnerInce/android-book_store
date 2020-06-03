package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SystemTraffic extends AppCompatActivity{

    private String operation_type;
    private String time_filter;

    private Spinner op , time;


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


            }
        });

    }


}
