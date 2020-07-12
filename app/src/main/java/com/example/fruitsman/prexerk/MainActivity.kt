package com.example.fruitsman.prexerk

import android.Manifest.permission.*
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //UI ZONE
        val staBtn = findViewById<Button>(R.id.staBtn)
        val setBtn = findViewById<Button>(R.id.setBtn)
        val dataBtn = findViewById<Button>(R.id.dataBtn)
        val conveBtn = findViewById<Button>(R.id.conveBtn)
        val initBtn = findViewById<Button>(R.id.init)
        val helpBtn = findViewById<Button>(R.id.help)

        //val commuBtn = findViewById<Button>(R.id.commuBtn)


        val pListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                //Toast.makeText(this@MainActivity, "", Toast.LENGTH_SHORT).show()
            }
            override fun onPermissionDenied(deniedPermissions: ArrayList<String?>) {
                Toast.makeText(
                    this@MainActivity,
                    "권한 거부\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        TedPermission.with(this)
            .setPermissionListener(pListener)
            .setRationaleMessage("다양한 기능을 사용하기 위해 아래의 권한이 필요합니다.\n저장공간 : 데이터 저장\n카메라 : 운동자세 인식\n마이크 : 음성 인식")
            .setDeniedMessage("권한을 거부하시면 다양한 기능을 사용하실 수 없습니다.\n[설정] > [권한] 에서 권한을 허용해 주십시오.")
            .setPermissions(READ_EXTERNAL_STORAGE, CAMERA, RECORD_AUDIO)
            .check()

        staBtn.setOnClickListener {
            val tranSta= Intent(this@MainActivity, StaActivity::class.java)
            tranSta.putExtra("index",1)
            startActivity(tranSta)
        }
        setBtn.setOnClickListener {
            val tranSet = Intent(this@MainActivity, SettingActivity::class.java)
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
        initBtn.setOnClickListener {
            var routineDB: SQLiteDatabase? =initDB();

            var sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_K (name TEXT,pose INTEGER,left1 INTEGER,left2 INTEGER,right1 INTEGER,right2 INTEGER,left3 INTEGER,left4 INTEGER,right3 INTEGER,right4 INTEGER,time INTEGER,accuracy INTEGER)"
            routineDB!!.execSQL(sqlCreateTbl)
            sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_R (name TEXT,ex1 TEXT,ex2 TEXT,ex3 TEXT,ex4 TEXT,ex5 TEXT,ex6 TEXT,ex7 TEXT,ex8 TEXT,ex9 TEXT,ex10 TEXT)"
            routineDB!!.execSQL(sqlCreateTbl)
            sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_D (name TEXT,name1 TEXT,name2 TEXT,name3 TEXT,name4 TEXT,name5 TEXT,name6 TEXT,name7 TEXT,name8 TEXT,name9 TEXT,name10 TEXT,set1 INTEGER, set2 INTEGER)"
            routineDB!!.execSQL(sqlCreateTbl)

            var sqlDelete:String = "DELETE FROM ROUTINE_K" ;
            routineDB!!.execSQL(sqlDelete)
            sqlDelete= "DELETE FROM ROUTINE_R" ;
            routineDB!!.execSQL(sqlDelete)
            sqlDelete= "DELETE FROM ROUTINE_D" ;
            routineDB!!.execSQL(sqlDelete)

            var sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                    "VALUES ("+"'"+"서있기"+"'"+","+0+","+-100+","+-90+","+-70+","+-80+","+-100+","+-90+","+-85+","+-90+","+5+","+15+")"
            routineDB!!.execSQL(sqlInsert)
            sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
            "VALUES ("+"'"+"숨들이마시기구분동작1"+"'"+","+0+","+-100+","+-90+","+-70+","+-80+","+-100+","+-90+","+-85+","+-90+","+5+","+15+")"
            routineDB!!.execSQL(sqlInsert)
            sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                    "VALUES ("+"'"+"숨들이마시기구분동작2"+"'"+","+170+","+170+","+1+","+1+","+-80+","+-100+","+-90+","+-85+","+-90+","+5+","+15+")"
            routineDB!!.execSQL(sqlInsert)
            sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                    "VALUES ("+"'"+"숨들이마시기구분동작3"+"'"+","+90+","+90+","+90+","+90+","+-80+","+-100+","+-90+","+-85+","+-90+","+5+","+15+")"
            routineDB!!.execSQL(sqlInsert)
            sqlInsert = "INSERT OR REPLACE INTO ROUTINE_R (name,ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10) " +
                    "VALUES ('"+"기초운동루틴"+"','"+"서있기"+"','"+"양팔벌려90도만들기"+"','"+"숨들이마시기"+"','"+"서있기"+"','"+"none"+"','"+"none"+"','"+"none"+"','"+"none"+"','"+"none"+"','"+"none"+"')"
            routineDB!!.execSQL(sqlInsert)
            sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                    "VALUES ("+"'"+"양팔벌려90도만들기"+"'"+","+170+","+170+","+1+","+1+","+-80+","+-100+","+-90+","+-85+","+-90+","+5+","+15+")"
            routineDB!!.execSQL(sqlInsert)
            sqlInsert = "INSERT OR REPLACE INTO ROUTINE_D (name ,name1 ,name2 ,name3 ,name4 ,name5 ,name6 ,name7 ,name8 ,name9 ,name10 ,set1 ,set2) " +
                    "VALUES ('"+"숨들이마시기"+"','"+"숨들이마시기구분동작1"+"','"+"숨들이마시기구분동작2"+"','"+"숨들이마시기구분동작3"+"','"+"숨들이마시기구분동작2"+"','"+"숨들이마시기구분동작1"+"','"+"none"+"','"+"none"+"','"+"none"+"','"+"none"+"','"+"none"+"',"+3+","+3+")"
            routineDB!!.execSQL(sqlInsert)
            val sharedPreferences =
                getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("routineName", "기초운동루틴")
            editor.commit();
            Toast.makeText(applicationContext, "초기화 완료", Toast.LENGTH_SHORT).show()

        }
        helpBtn.setOnClickListener{
            //도움말
            val tranConve = Intent(this@MainActivity, TuOne::class.java)
            tranConve.setFlags(FLAG_ACTIVITY_NO_HISTORY)
            startActivity(tranConve)
        }
        /*
        commuBtn.setOnClickListener {

            val tranCommu = Intent(this@MainActivity, CommuActivity::class.java)
            startActivity(tranCommu)
        }
        */
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
    fun initDB():SQLiteDatabase{
        Log.d("fffffffffffffff",filesDir.toString())
        val file= File(filesDir,"routine.db")
        var db= SQLiteDatabase.openOrCreateDatabase(file,null)
        if (db == null) {
            Log.d("tttttt","failed to create db" + file.getAbsolutePath()) ;
        }
        return db
    }
}
