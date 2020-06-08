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

import java.util.ArrayList;
import java.util.List;

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

        RecyclerView recyclerView = findViewById(R.id.system_traffic_recyclerview);
        final LogAdapter adapter = new LogAdapter(SystemTraffic.this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_type = op.getSelectedItem().toString();
                time_filter = time.getSelectedItem().toString();
                Toast.makeText(SystemTraffic.this,operation_type+" "+time_filter, Toast.LENGTH_LONG).show();
                log_type = operation_type;
                fill();
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void fill(){

        list.clear();
        for(int i = 0; i < 7; i++){
            LogObj temp = new LogObj(log_type,"xxxxx", "xxxxxxxxxxxxxx", "xxxxxx@xxxxx.com", "xxx xxx xx xx", "xxxxxxx xx xxxxx xx xxxxxxxxxx xxxxxxxxxxxx  xxxx x xxxxxx xxxxxx xxxx xxx", "12.12.2020", "add", "7", "xxxxx", "xxx.xx", "xxx", "xxxxxxx", "xxxxxxxxxx", "4", "xxxxxxxxxxxxxx xxxxxxxxxxxxxx xxxxxxxxxxxxxx xxxxxxxxxxxxx xxxxxxxxxxxx xxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxx. xxxxxxxxxxxxxx .xxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxxxx xxxxxxxxxxxxxxxxxxxx");
            list.add(temp);
        }

    }


}
