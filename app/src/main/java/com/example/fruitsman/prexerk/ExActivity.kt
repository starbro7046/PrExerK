package com.example.fruitsman.prexerk


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.AudioManager
import android.os.Bundle
import android.os.Environment
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.format.DateFormat
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ExActivity : AppCompatActivity() {

    var recordIntent: Intent? = null
    var sRecognizer: SpeechRecognizer? = null
    var audio: AudioManager? =null;

    private val FOLDER_NAME = "/PrExer_ScreenShots"

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

        recordIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recordIntent!!.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        recordIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        val data: SharedPreferences =getSharedPreferences("settings", Context.MODE_PRIVATE);
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
    private fun volUp(){
        audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI)
        audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI)

    }
    private fun volDown(){
        audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI)
        audio?.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI)

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
            val v1 = window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false

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
    public fun startStt(){
        runOnUiThread{
            sRecognizer = SpeechRecognizer.createSpeechRecognizer(this@ExActivity)
            sRecognizer!!.setRecognitionListener(listener)
            sRecognizer!!.startListening(recordIntent)
        }
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
            val tf:Fragment = getSupportFragmentManager().findFragmentById(R.id.container)!!;
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
        }
        override fun onResults(results: Bundle) {
            val speeches = results.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION
            )
            var breakerV:Boolean=false; //var for break 2 times
            for (i in speeches!!.indices) {
                for(iD in 0..5){ //except resume value
                    if(speeches[i].contains(datas[iD])){ //mactching word exists
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
                //Log.d("aaaaaaaaaaaaaaa",(speeches[i]))
            }
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
