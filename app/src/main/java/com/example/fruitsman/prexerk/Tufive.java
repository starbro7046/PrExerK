package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class Tufive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tufive);
        LinearLayout layout=findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tranConve = new Intent(Tufive.this, Tusix.class);
                tranConve.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                startActivity(tranConve);
            }
        });
    }
}
