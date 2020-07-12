package com.example.fruitsman.prexerk


import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.AudioManager
import android.os.Bundle
import android.os.Environment
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.function.BiPredicate


class ExKeepActivity : AppCompatActivity() {

    var audio: AudioManager? =null;
    var recordIntent: Intent? = null
    var sRecognizer: SpeechRecognizer? = null
    var datas= arrayOf("","","","","","","")
    private val FOLDER_NAME = "/PrExer_ScreenShots"

    lateinit var tts: TextToSpeech;
    var canCount:Boolean=false;
    lateinit var timer:Timer
    private final var FINISH_INTERVAL_TIME:Long = 2000;
    private var backPressedTime:Long = 0;
    private val REQ_CODE_SPEECH_INPUT = 100

    override fun onResume() {
        super.onResume()

        val time=getIntent().getIntExtra("time",10)
        Log.d("bbbbbbbbbbbbbbb","time:"+time)
        //getIntent().getIntExtra("accuracy",70)
        var elaspedAccurateTime:Int=0;
        var elaspedEntireTime:Int=0;

        val leftTIme=findViewById<TextView>(com.example.fruitsman.prexerk.R.id.leftTime);

        tts= TextToSpeech(this, TextToSpeech.OnInitListener { status->
            if(status!= TextToSpeech.ERROR){
                tts.setLanguage(Locale.KOREAN);
                //test.setPitch(1,)
            }
        })
        recordIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recordIntent!!.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        recordIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

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

        audio = getSystemService(Context.AUDIO_SERVICE) as AudioManager;

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
        if(data.getBoolean("end",false)){
            datas[0]= data.getString("endV","").toString()
        }
        if(data.getBoolean("pause",false)){
            datas[1]= data.getString("pauseV","").toString()
        }
        if(data.getBoolean("up",false)){
            datas[2]= data.getString("upV","").toString()

        }
        if(data.getBoolean("down",false)){
            datas[3]= data.getString("downV","").toString()

        }
        if(data.getBoolean("cap",false)){
            datas[4]= data.getString("capV","").toString()

        }
        if(data.getBoolean("reco",false)){
            datas[5]= data.getString("recoV","").toString()
        }
        if(data.getBoolean("resume",false)){
            datas[6]= data.getString("resumeV","").toString() //resume: index 6!!
        }

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
    public fun startStt(){
        runOnUiThread{
            sRecognizer = SpeechRecognizer.createSpeechRecognizer(this@ExKeepActivity)
            sRecognizer!!.setRecognitionListener(listener)
            sRecognizer!!.startListening(recordIntent)
        }
    }
    private fun capture(){
        val timenow= Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", timenow)

        try {
            val FOLDER_PATH =
                Environment.getExternalStorageDirectory().absolutePath + FOLDER_NAME        //fix later, api 29

            val folder = File(FOLDER_PATH)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val FILE_PATH = FOLDER_PATH + "/" + timenow + ".png"
            // create bitmap screen capture


            var a:View=findViewById(R.id.container2)
            var bitmap:Bitmap = Bitmap.createBitmap(a.getWidth(),a.getHeight(), Bitmap.Config.ARGB_8888);
            var canvas:Canvas = Canvas(bitmap);
            a.draw(canvas);

            /*
            val v1 = window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false
               */

            val imageFile = File(FILE_PATH)

            val outputStream = FileOutputStream(imageFile)
            val quality = 100


            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e:Throwable){
            e.printStackTrace()
        }
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
    private val listener: RecognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
            Toast.makeText(applicationContext, "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray) {}
        override fun onEndOfSpeech() {
            Log.d("aaaaaaaaaa","end")
            val tf: Fragment = getSupportFragmentManager().findFragmentById(R.id.container2)!!;
            (tf as CameraActivity).isListening=false;//crash warning

        }
        override fun onError(error: Int) {
            val message: String
            message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러입니다"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러입니다"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "권한이 없습니다"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러입니다"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트워크 시간초과입니다"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "인식기 사용 중입니다"
                SpeechRecognizer.ERROR_SERVER -> "서버가 에러입니다"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "음성 시간초과입니다"
                else -> "알 수 없는 오류입니다"
            }
            Toast.makeText(applicationContext, "에러가 발생하였습니다 : "+message, Toast.LENGTH_SHORT)
                .show()
        }private fun volUp(){
            audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI)
            audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI)

        }
        private fun volDown(){
            audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI)
            audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI)
        }

        override fun onResults(results: Bundle) {
            val speeches = results.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION
            )
            var breakerV:Boolean=false; //var for break 2 times
            for (i in speeches!!.indices) {
                for(iD in 0..5){ //except resume value
                    if(speeches[i].contains(datas[iD])){ //mactching word exists fix later?
                        Log.d("aaaaaaaaaaaaaaa",(speeches[i]))
                        when(iD){
                            0-> finishTo() //skip exercise
                            //1->  //pause exercise
                            2-> volUp() //vol up
                            3-> volDown()  //vol down
                            4-> capture()//capture
                            //5-> finishTo()   //record
                        }
                        breakerV=true;
                        break;
                    }
                }
                if(breakerV)
                    break;
            }
            if(!breakerV)
                Toast.makeText(applicationContext, "일치하는 명령어 없음", Toast.LENGTH_SHORT).show()
        }
        override fun onPartialResults(partialResults: Bundle) {}
        override fun onEvent(eventType: Int, params: Bundle) {}
    }
    override fun onDestroy() {
        tts.stop()
        sRecognizer?.destroy()
        super.onDestroy()
    }
}