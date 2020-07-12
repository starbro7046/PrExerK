package com.example.fruitsman.prexerk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kyleduo.switchbutton.SwitchButton


class ConveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conve)


        val data:SharedPreferences=getSharedPreferences("settings", Context.MODE_PRIVATE);

        val editor = data.edit()

        val zestureButton = findViewById(R.id.zesture) as SwitchButton
        zestureButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("zes", true);
                editor.commit()
            } else {
                editor.putBoolean("zes", false);
                editor.commit()
            }
        }
        val endButton = findViewById(R.id.end) as SwitchButton
        endButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("end", true);
                editor.commit()
            } else {
                editor.putBoolean("end", false);
                editor.commit()
            }
        }
        val pauseButton = findViewById(R.id.pause) as SwitchButton
        pauseButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("pause", true);
                editor.commit()
            } else {
                editor.putBoolean("pause", false);
                editor.commit()
            }
        }
        val resumeButton = findViewById(R.id.resume) as SwitchButton
        resumeButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("resume", true);
                editor.commit()
            } else {
                editor.putBoolean("resume", false);
                editor.commit()
            }
        }
        val upButton = findViewById(R.id.up) as SwitchButton
        upButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("up", true);
                editor.commit()
            } else {
                editor.putBoolean("up", false);
                editor.commit()
            }
        }
        val downButton = findViewById(R.id.down) as SwitchButton
        downButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("down", true);
                editor.commit()
            } else {
                editor.putBoolean("down", false);
                editor.commit()
            }
        }
        val capButton = findViewById(R.id.cap) as SwitchButton
        capButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("cap", true);
                editor.commit()
            } else {
                editor.putBoolean("cap", false);
                editor.commit()
            }
        }
        val recoButton = findViewById(R.id.reco) as SwitchButton
        recoButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("reco", true);
                editor.commit()
            } else {
                editor.putBoolean("reco", false);
                editor.commit()
            }
        }
        val endB=findViewById<ImageButton>(R.id.end2)
        endB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","endV")
            startActivity(tranSta)
        })
        val pauseB=findViewById<ImageButton>(R.id.pause2)
        pauseB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","pauseV")
            startActivity(tranSta)
        })
        val resumeB=findViewById<ImageButton>(R.id.resume2)
        resumeB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","resumeV")
            startActivity(tranSta)
        })
        val upB=findViewById<ImageButton>(R.id.up2)
        upB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","upV")
            startActivity(tranSta)
        })
        val downB=findViewById<ImageButton>(R.id.down2)
        downB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","downV")
            startActivity(tranSta)
        })
        val capB=findViewById<ImageButton>(R.id.cap2)
        capB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","capV")
            startActivity(tranSta)
        })
        val recoB=findViewById<ImageButton>(R.id.reco2)
        recoB.setOnClickListener(View.OnClickListener {
            val tranSta= Intent(this@ConveActivity, VoiceSetting::class.java)
            tranSta.putExtra("name","recoV")
            startActivity(tranSta)
        })

        var speed:Int=data.getInt("speed",10);
        val speedT=findViewById<TextView>(R.id.speedt)
        speedT.setText("빠르기: "+(speed/10f))
        val upButton2 = findViewById(R.id.up3) as ImageButton
        upButton2.setOnClickListener(View.OnClickListener {
            if(speed<30){
                speed +=1
                speedT.setText("빠르기: "+(speed/10f))
                editor.putInt("speed",speed)
                editor.commit()
                Log.d("qqqqqqqqqqqq",speed.toString())
            }else{
                //max
                Toast.makeText(applicationContext, "최대", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        val downButton2 = findViewById(R.id.down3) as ImageButton
        downButton2.setOnClickListener(View.OnClickListener {
            if(speed>1){
                speed -=1
                speedT.setText("빠르기: "+(speed/10f))
                editor.putInt("speed",speed)
                editor.commit()
                Log.d("qqqqqqqqqqqq",speed.toString())
            }else{
                //min
                Toast.makeText(applicationContext, "최소", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        if(data.getBoolean("zes",false)){
            zestureButton.toggleNoEvent()
        }
        if(data.getBoolean("end",false)){
            endButton.toggleNoEvent()
        }
        if(data.getBoolean("pause",false)){
            pauseButton.toggleNoEvent()
        }
        if(data.getBoolean("resume",false)){
            resumeButton.toggleNoEvent()
        }
        if(data.getBoolean("up",false)){
            upButton.toggleNoEvent()
        }
        if(data.getBoolean("down",false)){
            downButton.toggleNoEvent()
        }
        if(data.getBoolean("cap",false)){
            capButton.toggleNoEvent()
        }
        if(data.getBoolean("reco",false)){
            recoButton.toggleNoEvent()
        }

    }
}
