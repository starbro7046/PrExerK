package com.example.fruitsman.prexerk

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class ExKeepActivity : AppCompatActivity() {
    lateinit var tts: TextToSpeech;
    var canCount:Boolean=false;
    lateinit var timer:Timer
    private final var FINISH_INTERVAL_TIME:Long = 2000;
    private var backPressedTime:Long = 0;
    private val REQ_CODE_SPEECH_INPUT = 100

    override fun onResume() {
        super.onResume()


        val time=getIntent().getIntExtra("time",10)
        //getIntent().getIntExtra("accuracy",70)
        var elaspedAccurateTime:Int=0;
        var elaspedEntireTime:Int=0;

        val accuracy=findViewById<TextView>(com.example.fruitsman.prexerk.R.id.accuracy);
        val leftTIme=findViewById<TextView>(com.example.fruitsman.prexerk.R.id.leftTime);

        tts= TextToSpeech(this, TextToSpeech.OnInitListener { status->
            if(status!= TextToSpeech.ERROR){
                tts.setLanguage(Locale.KOREAN);
                //test.setPitch(1,)
            }
        })
        leftTIme.setText("남은시간: "+(time-elaspedAccurateTime)+" 초")
        timer = Timer()
        val TT: TimerTask = object : TimerTask() {
            override fun run() { // 1초마다 검사
                if(canCount){
                    Log.d("eeeeeeeeee","called")
                    elaspedAccurateTime++;
                    leftTIme.setText("남은시간: "+(time-elaspedAccurateTime)+" 초")
                }else{
                    //is not accurate
                    Log.d("eeeeeeeeee","no")
                }
                if(time<=elaspedAccurateTime){
                    //end
                    finishTo()
                }
            }
        }
        timer.schedule(TT, 0, 1000) //Timer 실행

        /*
        val countDownTimer = object : CountDownTimer(time.toLong()*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                leftTIme.setText("남은시간: "+millisUntilFinished/1000L+" 초")

            }
            override fun onFinish() {
                leftTIme.text = "Done."
                finishTo()
            }
        }.start()
        */

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex_keep)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container2,CameraActivity())
            .commit()
        tts= TextToSpeech(this, TextToSpeech.OnInitListener { status->
            if(status!= TextToSpeech.ERROR){
                tts.setLanguage(Locale.KOREAN);
                //test.setPitch(1,)
            }
        })
        val data:SharedPreferences=getSharedPreferences("settings", Context.MODE_PRIVATE);
        val speed=data.getInt("speed",10)
        tts.setSpeechRate(speed/10f)
    }
    public fun stopTts(){
        tts.stop();
    }
    public fun finishTo(){
        val tranSta= Intent(this@ExKeepActivity, StaActivity::class.java)
        tranSta.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT)
        timer.cancel() //타이머 종료
        tts.stop()
        startActivity(tranSta)
    }
    public fun start(){

    }
    public fun pause(){

    }
    @Synchronized
    fun setBool(cancount:Boolean) {
        canCount=cancount;
    }
    public fun speak(text:String){
        val utteranceId = this.hashCode().toString() + ""
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,utteranceId);
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
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    (result[0])
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}