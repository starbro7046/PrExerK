package com.example.fruitsman.prexerk

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.content.Intent.getIntentOld
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class CameraGet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_get)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container3,CameraActivity())
            .commit()

        val capBtn:Button=findViewById(R.id.cap)
        capBtn.setOnClickListener{
            val tf: Fragment = getSupportFragmentManager().findFragmentById(R.id.container3)!!;
            (tf as CameraActivity).captureThis=true;//crash warning
        }
    }
    public fun finishTo(left1:Int,left2:Int,right1:Int,right2:Int,left3:Int,left4:Int,right3:Int,right4:Int){

        val intentV:Intent=Intent(this@CameraGet,Plus_K::class.java)

        intentV.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT)
        intentV.putExtra("left1", left1)
        intentV.putExtra("left2",left2)
        intentV.putExtra("left3",left3)
        intentV.putExtra("left4", left4)
        intentV.putExtra("right1",right1)
        intentV.putExtra("right2",right2)
        intentV.putExtra("right3",right3)
        intentV.putExtra("right4",right4)
        startActivity(intentV);
    }
}
