package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class Plus_K extends AppCompatActivity {
    public  SQLiteDatabase routineDB;

    int pose=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus__k);

        final EditText name=findViewById(R.id.name);
        final EditText left1=findViewById(R.id.left1);
        final EditText left2=findViewById(R.id.left2);
        final EditText right1=findViewById(R.id.right1);
        final EditText right2=findViewById(R.id.right2);
        final EditText left3=findViewById(R.id.left3);
        final EditText left4=findViewById(R.id.left4);
        final EditText right3=findViewById(R.id.right3);
        final EditText right4=findViewById(R.id.right4);
        final EditText time=findViewById(R.id.time);
        final EditText accuracy=findViewById(R.id.accuracy);

        routineDB=initDB();
        initTableK();

        final Intent intent=getIntent();
        if(intent.getBooleanExtra("ex",false)) {
            name.setText(intent.getStringExtra("name"));
            left1.setText(""+intent.getIntExtra("left1",0));
            left2.setText(""+intent.getIntExtra("left2",0));
            left3.setText(""+intent.getIntExtra("left3",0));
            left4.setText(""+intent.getIntExtra("left4",0));
            right1.setText(""+intent.getIntExtra("right1",0));
            right2.setText(""+intent.getIntExtra("right2",0));
            right3.setText(""+intent.getIntExtra("right3",0));
            right4.setText(""+intent.getIntExtra("right4",0));
            time.setText(""+intent.getIntExtra("time",0));
            accuracy.setText(""+intent.getIntExtra("acc",0));

        }
        ImageButton saveB=findViewById(R.id.save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 각도 확인 코드 추가 예정
                if(name.getText().toString().equals("")||left1.getText().toString().equals("")||left2.getText().toString().equals("")||left3.getText().toString().equals("")||left4.getText().toString().equals("")
                ||right1.getText().toString().equals("")||right2.getText().toString().equals("")||right3.getText().toString().equals("")||right4.getText().toString().equals("")||time.getText().toString().equals("")
                        ||accuracy.getText().toString().equals("")){
                    Toast.makeText(Plus_K.this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    if(getIntent().getBooleanExtra("ex",false)){
                        updateTableK(intent.getStringExtra("name"),name.getText().toString(),Integer.parseInt(left1.getText().toString()),Integer.parseInt(left2.getText().toString()),Integer.parseInt(right1.getText().toString()),
                                Integer.parseInt(right2.getText().toString()),Integer.parseInt(left3.getText().toString()),Integer.parseInt(left4.getText().toString()),Integer.parseInt(right3.getText().toString()),
                                Integer.parseInt(right4.getText().toString()),Integer.parseInt(time.getText().toString()),Integer.parseInt(accuracy.getText().toString()));
                        Toast.makeText(Plus_K.this, "수정되었습니다", Toast.LENGTH_SHORT).show(); //수정
                    }else{
                        insertTableK(name.getText().toString(),pose,Integer.parseInt(left1.getText().toString()),Integer.parseInt(left2.getText().toString()),Integer.parseInt(right1.getText().toString()),
                                Integer.parseInt(right2.getText().toString()),Integer.parseInt(left3.getText().toString()),Integer.parseInt(left4.getText().toString()),Integer.parseInt(right3.getText().toString()),
                                Integer.parseInt(right4.getText().toString()),Integer.parseInt(time
                                        .getText().toString()),Integer.parseInt(accuracy.getText().toString()));
                        Toast.makeText(Plus_K.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ImageButton backB=findViewById(R.id.back);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });ImageButton camB=findViewById(R.id.cam);
        camB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExData dataE= ExData.getInstance();
                dataE.exCode=2;
                Intent tranSet = new Intent(Plus_K.this, CameraGet.class);
                tranSet.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                startActivity(tranSet);
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {//called by camera get
        final EditText left1=findViewById(R.id.left1);
        final EditText left2=findViewById(R.id.left2);
        final EditText right1=findViewById(R.id.right1);
        final EditText right2=findViewById(R.id.right2);
        final EditText left3=findViewById(R.id.left3);
        final EditText left4=findViewById(R.id.left4);
        final EditText right3=findViewById(R.id.right3);
        final EditText right4=findViewById(R.id.right4);
        left1.setText(""+intent.getIntExtra("left1",0));
        left2.setText(""+intent.getIntExtra("left2",0));
        left3.setText(""+intent.getIntExtra("left3",0));
        left4.setText(""+intent.getIntExtra("left4",0));
        right1.setText(""+intent.getIntExtra("right1",0));
        right2.setText(""+intent.getIntExtra("right2",0));
        right3.setText(""+intent.getIntExtra("right3",0));
        right4.setText(""+intent.getIntExtra("right4",0));
        super.onNewIntent(intent);
    }
    @Override
    protected void onResume() {

        super.onResume();
    }

    private void initTableK(){//fix later
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_K (name TEXT,pose INTEGER,left1 INTEGER,left2 INTEGER,right1 INTEGER,right2 INTEGER,left3 INTEGER,left4 INTEGER,right3 INTEGER,right4 INTEGER,time INTEGER,accuracy INTEGER)";
        routineDB.execSQL(sqlCreateTbl);
    }
    private SQLiteDatabase initDB(){
        File file= new File(getFilesDir(),"routine.db");
        SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(file,null);
        if (db == null) {
            Log.d("tttttt","failed to create db" + file.getAbsolutePath()) ;
        }
        return db;
    }private void insertTableK(String name,int pose,int left1,int left2,int right1,int right2,int left3,int left4,int right3,int right4,int time,int accuracy){

        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                "VALUES ("+"'"+name+"'"+","+pose+","+left1+","+left2+","+right1+","+right2+","+left3+","+left4+","+right3+","+right4+","+time+","+accuracy+")";
        routineDB.execSQL(sqlInsert);
    }private void updateTableK(String original,String name,int left1,int left2,int right1,int right2,int left3,int left4,int right3,int right4,int time,int accuracy){

        String sqlInsert = "UPDATE ROUTINE_K SET "+"name='"+name+"',left1="+left1+", left2="+left2+", left3="+left3+", left4="+left4+", right1="+right1+", right2="+right2+", right3="+right3+", right4="+right4+"" +
                ", time="+time+", accuracy="+accuracy+" WHERE name='"+original+"'";
        Log.d("bbbbbbbbbbbbbb",sqlInsert);
        routineDB.execSQL(sqlInsert);
    }
}
