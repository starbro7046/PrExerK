package com.example.fruitsman.prexerk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val TAG = "TEST:"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //UI ZONE
        val staBtn = findViewById<Button>(R.id.staBtn)
        val setBtn = findViewById<Button>(R.id.setBtn)
        val dataBtn = findViewById<Button>(R.id.dataBtn)
        val conveBtn = findViewById<Button>(R.id.conveBtn)
        val commuBtn = findViewById<Button>(R.id.commuBtn)

        staBtn.setOnClickListener {
            val tranSta= Intent(this@MainActivity, StaActivity::class.java)
            tranSta.putExtra("index",1)
            startActivity(tranSta)
        }
        setBtn.setOnClickListener {
            val tranSet = Intent(this@MainActivity, ExKeepActivity::class.java)
            startActivity(tranSet)
        }
        dataBtn.setOnClickListener {
            val tranData = Intent(this@MainActivity, DataActivity::class.java)
            startActivity(tranData)
        }
        conveBtn.setOnClickListener {
            val tranConve = Intent(this@MainActivity, ConveActivity::class.java)
            startActivity(tranConve)
        }
        commuBtn.setOnClickListener {

            val tranCommu = Intent(this@MainActivity, CommuActivity::class.java)
            startActivity(tranCommu)
        }
        /*
        var a:IntArray= intArrayOf(1,2,3,4,5,6,7,8)
        var data:ExData=ExData.getInstance()
        data.setData(a,)
        Log.d("hhhhhhhh",data.stanGradient[3].toString())
         */
        /*
        val data: SharedPreferences =getSharedPreferences("settings", Context.MODE_PRIVATE);

        val editor = data.edit()
        if(data.getBoolean("none",true)){
            //init
        }
        */
    }
}
