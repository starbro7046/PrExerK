package com.example.fruitsman.prexerk

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ex.*
import kotlinx.android.synthetic.main.activity_ex.view.*
import kotlinx.android.synthetic.main.activity_sta.*
import java.io.File
import java.util.*


class StaActivity : AppCompatActivity() {
    lateinit var context2:Context;
    lateinit var timer:Timer;
    public var currentTaskN=1;
    public var routineDB: SQLiteDatabase? =null;
    //lateinit var tts: TextToSpeech;
    private final var FINISH_INTERVAL_TIME:Long = 2000;
    private var backPressedTime:Long = 0;
    var validTime:Int=0
    var eTime:Int=0;
    var restTimeV:Int=0;
    lateinit var elapsedTime:TextView;
   // lateinit var remainingTime:TextView;
    var voiceVar:Boolean=false;
    var restTime:TextView?=null;
    var lockS=false;// 두번 실행 방지
    lateinit var TT: TimerTask;
    lateinit var routineName: String;

    override fun onResume() {
        super.onResume()

        lockS=false;
        restTimeV=0;
        validTime=10;//휴식시간
        //var datas:Datas=Datas()
        //datas.setData(3)

        if(currentTaskN==1){
            currentTask.setText(loadValuesR(routineName,currentTaskN))
            afterTask.setText(loadValuesR(routineName,currentTaskN+1))
        }else{
            speak("휴식시간")
            //beforeTask.setText(loadValuesR(routineName,currentTaskN-1))
            currentTask.setText(loadValuesR(routineName,currentTaskN))
            afterTask.setText(loadValuesR(routineName,currentTaskN+1))
        }
        Log.d("yyyyyyyyyyyu",currentTaskN.toString())
        //countTimer(5)
        countTimer(10)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sta)

        context2=this;
        /*
        tts= TextToSpeech(this, TextToSpeech.OnInitListener { status->
            if(status!= TextToSpeech.ERROR){
                tts.setLanguage(Locale.KOREAN);
                //test.setPitch(1,)
            }
        })*/
        val data: SharedPreferences =getSharedPreferences("settings", Context.MODE_PRIVATE);
        routineName= data.getString("routineName","기본 운동루틴")!!;
        val speed=data.getInt("speed",10)
        //tts.setSpeechRate(speed/10f)
        val routine=findViewById<TextView>(R.id.routine);
        //val beforeTask=findViewById<TextView>(R.id.beforeTask);
        val currentTask=findViewById<TextView>(R.id.currentTask);
        val afterTask=findViewById<TextView>(R.id.afterTask);
        elapsedTime=findViewById<TextView>(R.id.elapsedTime);
        //remainingTime=findViewById<TextView>(R.id.remainingTime);
        restTime=findViewById<TextView>(R.id.restTime);
        //test
        val postBtn=findViewById<ToggleButton>(R.id.postBtn);
        //val postTBtn=findViewById<ImageButton>(R.id.postTBtn);

        routineDB=initDB();
        voiceVar=data.getBoolean("zes",false)

        initTableK()
        initTableR()
        initTableD()
        //초기화

        /*
        insertTableK("s1k",0,-99,-99,-72,-81,-90,-88,-83,-92,5,10)
        insertTableK("s2k",0,115,75,72,96,-114,-108,-63,-67,5,15)

        insertTableD("s1d","s1k","s2k","s1k","s2k","s1k","s2k","s1k","s2k","s1k","s2k",3,3)

        insertTableR("homeRoutine","ff","s1k","none","none","none","none","none","none","none","none")
        */
        Log.d("yyyyyyyyyy",loadValuesK("standr",10))

        currentTaskN=getIntent().getIntExtra("index",1)

        routine.setText(loadValuesR(routineName,0))
        Log.d("yyyyyyyyyyyu",currentTaskN.toString())
        //countTimer(5)//add restime later


        /*
        postTBtn.setOnClickListener{
            validTime+=10;
        }

         */

    }fun initDB():SQLiteDatabase{
        Log.d("fffffffffffffff",filesDir.toString())
        val file=File(filesDir,"routine.db")
        var db= SQLiteDatabase.openOrCreateDatabase(file,null)
        if (db == null) {
            Log.d("tttttt","failed to create db" + file.getAbsolutePath()) ;
        }
        return db
    }fun initTableK(){//fix later
        val sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_K (name TEXT,pose INTEGER,left1 INTEGER,left2 INTEGER,right1 INTEGER,right2 INTEGER,left3 INTEGER,left4 INTEGER,right3 INTEGER,right4 INTEGER,time INTEGER,accuracy INTEGER)"
        routineDB!!.execSQL(sqlCreateTbl)
    }fun initTableR(){
        val sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_R (name TEXT,ex1 TEXT,ex2 TEXT,ex3 TEXT,ex4 TEXT,ex5 TEXT,ex6 TEXT,ex7 TEXT,ex8 TEXT,ex9 TEXT,ex10 TEXT)"
        routineDB!!.execSQL(sqlCreateTbl)
    }fun initTableD(){
        val sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_D (name TEXT,name1 TEXT,name2 TEXT,name3 TEXT,name4 TEXT,name5 TEXT,name6 TEXT,name7 TEXT,name8 TEXT,name9 TEXT,name10 TEXT,set1 INTEGER, set2 INTEGER)"
        routineDB!!.execSQL(sqlCreateTbl)
    }fun insertTableK(name:String,pose:Int,left1:Int,left2:Int,right1:Int,right2:Int,left3:Int,left4:Int,right3:Int,right4:Int,time:Int,accuracy:Int){

        val sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                "VALUES ("+"'"+name+"'"+","+pose+","+left1+","+left2+","+right1+","+right2+","+left3+","+left4+","+right3+","+right4+","+time+","+accuracy+")"
        routineDB!!.execSQL(sqlInsert)
    }fun insertTableR(name: String,ex1: String,ex2: String,ex3: String,ex4: String,ex5: String,ex6: String,ex7: String,ex8: String,ex9: String,ex10: String){
        val sqlInsert = "INSERT OR REPLACE INTO ROUTINE_R (name,ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10) " +
                "VALUES ('"+name+"','"+ex1+"','"+ex2+"','"+ex3+"','"+ex4+"','"+ex5+"','"+ex6+"','"+ex7+"','"+ex8+"','"+ex9+"','"+ex10+"')"
        routineDB!!.execSQL(sqlInsert)
    }fun insertTableD(name: String,name1: String,name2: String,name3: String,name4: String,name5: String,name6: String,name7: String,name8: String,name9: String,name10: String,set1:Int,set2:Int){//++","
        val sqlInsert = "INSERT OR REPLACE INTO ROUTINE_D (name ,name1 ,name2 ,name3 ,name4 ,name5 ,name6 ,name7 ,name8 ,name9 ,name10 ,set1 ,set2) " +
                "VALUES ('"+name+"','"+name1+"','"+name2+"','"+name3+"','"+name4+"','"+name5+"','"+name6+"','"+name7+"','"+name8+"','"+name9+"','"+name10+"',"+set1+","+set2+")"

        routineDB!!.execSQL(sqlInsert)
    }
    fun loadValuesK(name:String,index1:Int):String{
        if(routineDB!=null)
        {
            val sqlQuerySel = "SELECT * FROM ROUTINE_K WHERE name='"+name+"'"
            var cursor:Cursor?=null
            cursor=routineDB!!.rawQuery(sqlQuerySel,null)
            if(cursor.moveToNext()){
                return cursor.getString(index1)
            }else{
                return "n";
            }
        }
        return "none"
    }
    fun loadValuesD(name:String,index:Int):String{
        if(routineDB!=null)
        {
            val sqlQuerySel = "SELECT * FROM ROUTINE_D WHERE name='"+name+"'"
            var cursor:Cursor?=null

            cursor=routineDB!!.rawQuery(sqlQuerySel,null)
            if(cursor.moveToNext()){
                return cursor.getString(index)
            }else{
                return "n";
            }
        }
        return "none";
    }
    fun loadValuesR(name:String, index:Int):String{
        if(routineDB!=null)
        {
            val sqlQuerySel = "SELECT * FROM ROUTINE_R WHERE name='"+name+"'"
            var cursor:Cursor?=null
            cursor=routineDB!!.rawQuery(sqlQuerySel,null)
            if(cursor.moveToNext()) {
                return cursor.getString(index)
            }
        }
        return "none";
    }
    public fun speak(text:String){
        val utteranceId = this.hashCode().toString() + ""
        //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,utteranceId);
    }
    fun countTimer(time:Int){
        if(currentTaskN==11||loadValuesK(loadValuesR(routineName,currentTaskN),10)=="none") {
            //Log.d("yyyyyyy", loadValuesR(num))
            return
        }
        TT= object : TimerTask() {
            override fun run() { // 1초마다 검사
                eTime++;
                restTimeV++;
                if (postBtn.isChecked) {
                    //validTime
                } else {
                    validTime--;
                }
                (context2 as StaActivity).runOnUiThread{
                    elapsedTime.setText("경과시간: " + eTime)
                    //remainingTime.setText("휴식시간" + restTimeV)
                    restTime?.setText("남은시간: " + validTime);
                }
                if (validTime <= 0 && lockS==false) {
                    (context2 as StaActivity).runOnUiThread {
                        restTime!!.setText("완료")
                    }
                    lockS=true;
                    val eName: String = loadValuesR(routineName,currentTaskN)
                    Log.d("bbbbbbbbbbb"," the value:"+currentTaskN)

                    currentTaskN++;

                    val dataE: ExData = ExData.getInstance()
                    dataE.useVoiceRecognition=voiceVar;//보이스 설정
                    if (loadValuesK(eName, 0) != "n") {
                        //static exercise
                        //searched in db
                        dataE.setData(
                            intArrayOf(
                                loadValuesK(eName, 2).toInt(),
                                loadValuesK(eName, 3).toInt(),
                                loadValuesK(eName, 4).toInt(),
                                loadValuesK(eName, 5).toInt(),
                                loadValuesK(eName, 6).toInt(),
                                loadValuesK(eName, 7).toInt(),
                                loadValuesK(eName, 8).toInt(),
                                loadValuesK(eName, 9).toInt()
                            ),
                            loadValuesK(eName, 11).toInt()
                        )
                        dataE.exCode = 0;
                        val tranStaEx = Intent(this@StaActivity, ExKeepActivity::class.java)
                        tranStaEx.putExtra("time", loadValuesK(eName, 10).toInt())
                        tranStaEx.putExtra("accuracy", loadValuesK(eName, 11).toInt())
                        timer.cancel();
                        startActivity(tranStaEx)

                    } else if (loadValuesD(eName, 0) != "n") {//1~10
                        Log.d("qqqqqqqqqqqqqq", "one")
                        for (i in 1..10) {
                            val eName2: String = loadValuesD(eName, i)
                            Log.d("qqqqqqqqqqqqqq", eName2)
                            if (eName2 == "none") {
                                //end
                                dataE.dyLength = i;
                                Log.d("qqqqqqqqqqqqqq", "two")
                                break;
                            } else {
                                if (loadValuesK(eName2, 0) != "n") {
                                    dataE.setDataD(
                                        intArrayOf(
                                            loadValuesK(eName2, 2).toInt(),
                                            loadValuesK(eName2, 3).toInt(),
                                            loadValuesK(eName2, 4).toInt(),
                                            loadValuesK(eName2, 5).toInt(),
                                            loadValuesK(eName2, 6).toInt(),
                                            loadValuesK(eName2, 7).toInt(),
                                            loadValuesK(eName2, 8).toInt(),
                                            loadValuesK(eName2, 9).toInt(),
                                            loadValuesK(eName2, 11).toInt()
                                        ), i - 1
                                    )
                                    if (i == 10) {
                                        dataE.dyLength = 10;
                                    }
                                } else {
                                    //error
                                    Log.d("qqqqqqqqqqqqq", "error occured");
                                }
                            }
                        }
                        val tranStaEx = Intent(this@StaActivity, ExActivity::class.java)
                        dataE.set1=loadValuesD(eName, 11).toInt()
                        dataE.set2=loadValuesD(eName, 12).toInt()
                        tranStaEx.putExtra("set1", loadValuesD(eName, 11).toInt())
                        tranStaEx.putExtra("set2", loadValuesD(eName, 12).toInt())
                        timer.cancel();
                        startActivity(tranStaEx)
                    }else{
                        //Toast.makeText(this@StaActivity, "일치하는 운동 없음", Toast.LENGTH_SHORT).show()
                        timer.cancel()
                    }
                }
            }
        }
        timer = Timer()
        timer.schedule(TT, 0, 1000) //Timer 실행
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
}
