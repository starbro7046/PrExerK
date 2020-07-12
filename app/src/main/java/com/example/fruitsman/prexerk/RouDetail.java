package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RouDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rou_detail);

        SharedPreferences sharedPreferences=getSharedPreferences("settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Intent intent=getIntent();

        TextView name=findViewById(R.id.name);
        TextView name1=findViewById(R.id.name1);
        TextView name2=findViewById(R.id.name2);
        TextView name3=findViewById(R.id.name3);
        TextView name4=findViewById(R.id.name4);
        TextView name5=findViewById(R.id.name5);
        TextView name6=findViewById(R.id.name6);
        TextView name7=findViewById(R.id.name7);
        TextView name8=findViewById(R.id.name8);
        TextView name9=findViewById(R.id.name9);
        TextView name10=findViewById(R.id.name10);

        String[] data=intent.getStringArrayExtra("exs");
        name.setText(""+intent.getStringExtra("name"));
        name1.setText(data[0]);
        name2.setText(data[1]);
        name3.setText(data[2]);
        name4.setText(data[3]);
        name5.setText(data[4]);
        name6.setText(data[5]);
        name7.setText(data[6]);
        name8.setText(data[7]);
        name9.setText(data[8]);
        name10.setText(data[9]);

        ImageButton seleB = findViewById(R.id.sele);
        seleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rou=intent.getStringExtra("name").toString();
                editor.putString("routineName",rou);
                editor.commit();
                Toast.makeText(RouDetail.this, "선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton backB = findViewById(R.id.back);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
