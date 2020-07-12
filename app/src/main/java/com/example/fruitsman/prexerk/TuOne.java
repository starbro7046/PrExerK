package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class TuOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_one);

        LinearLayout layout=findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tranConve = new Intent(TuOne.this, Tutwo.class);
                tranConve.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                startActivity(tranConve);
            }
        });

    }
}
