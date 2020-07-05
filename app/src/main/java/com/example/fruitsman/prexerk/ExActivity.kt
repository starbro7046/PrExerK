package com.example.fruitsman.prexerk

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Array
import java.util.*

class ExActivity : AppCompatActivity() {
    var canCount:Boolean=false;
    lateinit var tts: TextToSpeech;
    private final var FINISH_INTERVAL_TIME:Long = 2000;
    private var backPressedTime:Long = 0;
    lateinit var count:TextView;
    lateinit var set:TextView
    var datas= arrayOf("","","","","","","")

    private val REQ_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex)
        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container,CameraActivity())
            .commit()

        count=findViewById<TextView>(R.id.count)
        set=findViewById<TextView>(R.id.set)

        count.setText("0"+"/"+getIntent().getIntExtra("set1",3)+" 개");
        set.setText("0"+"/"+getIntent().getIntExtra("set2",3)+" 세트");

        tts= TextToSpeech(this, TextToSpeech.OnInitListener { status->
            if(status!= TextToSpeech.ERROR){
                tts.setLanguage(Locale.KOREAN);
                //test.setPitch(1,)
            }
        })
        val data: SharedPreferences =getSharedPreferences("settings", Context.MODE_PRIVATE);
        if(data.getBoolean("end",false)){
            datas[0]= data.getString("end","").toString()
        }
        if(data.getBoolean("pause",false)){
            datas[1]= data.getString("pause","").toString()
        }
        if(data.getBoolean("resume",false)){
            datas[2]= data.getString("resume","").toString()

        }
        if(data.getBoolean("up",false)){
            datas[3]= data.getString("up","").toString()

        }
        if(data.getBoolean("down",false)){
            datas[4]= data.getString("down","").toString()

        }
        if(data.getBoolean("cap",false)){
            datas[5]= data.getString("cap","").toString()

        }
        if(data.getBoolean("reco",false)){
            datas[6]= data.getString("reco","").toString()

        }
        val speed=data.getInt("speed",10)
        tts.setSpeechRate(speed/10f)
    }
    public fun speak(text:String){

        val utteranceId = this.hashCode().toString() + ""
        //Log.d("hhhhhhhhhh",utteranceId)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,utteranceId);
    }
    public fun stopTts(){
        tts.stop();
    }
    public fun finishTo(){
        val tranSta= Intent(this@ExActivity, StaActivity::class.java)
        tranSta.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        tts.stop()
        tts.shutdown()
        startActivity(tranSta)
    }
    public fun setSet(set1:Int,set2:Int,setEx1:Int,setEx2:Int){
        set.setText(set2.toString()+"/"+setEx2+" 세트");
        count.setText(set1.toString()+"/"+setEx1+" 개");
    }
    @Synchronized
    fun setBool(cancount:Boolean) {
        canCount=cancount;
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        val tempTime = System.currentTimeMillis()
        val intervalTime: Long = tempTime - backPressedTime

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            finishAffinity()
            //super.onBackPressed()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르면 종료", Toast.LENGTH_SHORT)
                .show()
        }
    }

    public fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "speak"
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "no supported",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    var result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(result.equals("")){

                    }else {
                        when (result[0]) {
                            datas[0] ->finishTo()
                            datas[1]-> Log.d("eeeeee","pause")
                            datas[2]->Log.d("eeeeee","resume")
                            datas[3]->Log.d("eeeeee","vol up")
                            datas[4]->Log.d("eeeeee","vol down")
                            datas[5]->Log.d("eeeeee","capture")
                            datas[6]->Log.d("eeeeee","record")
                        }
                    }
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}
