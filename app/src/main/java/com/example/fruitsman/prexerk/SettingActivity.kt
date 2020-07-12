package com.example.fruitsman.prexerk

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.io.File

class SettingActivity : AppCompatActivity() {
    public var routineDB: SQLiteDatabase? =null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)



        val staB:Button=findViewById(R.id.sta);
        staB.setOnClickListener {
            val tranSet = Intent(this@SettingActivity, SettingSta::class.java)
            startActivity(tranSet)
        }
        val dyB:Button=findViewById(R.id.dy);
        dyB.setOnClickListener {
            val tranSet = Intent(this@SettingActivity, SettingDy::class.java)
            startActivity(tranSet)
        }
        val rouB:Button=findViewById(R.id.rou);
        rouB.setOnClickListener {
            val tranSet = Intent(this@SettingActivity, SettingRou::class.java)
            startActivity(tranSet)
        }
        val selrouB:Button=findViewById(R.id.selrou);
        selrouB.setOnClickListener {
            val tranSet = Intent(this@SettingActivity, SeleRou::class.java)
            startActivity(tranSet)
        }

    }
}
