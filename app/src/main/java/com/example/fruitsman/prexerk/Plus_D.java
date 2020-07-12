package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class Plus_D extends AppCompatActivity {

    public  SQLiteDatabase routineDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus__d);

        final EditText name = findViewById(R.id.name);
        final EditText name1 = findViewById(R.id.name1);
        final EditText name2 = findViewById(R.id.name2);
        final EditText name3 = findViewById(R.id.name3);
        final EditText name4 = findViewById(R.id.name4);
        final EditText name5 = findViewById(R.id.name5);
        final EditText name6 = findViewById(R.id.name6);
        final EditText name7 = findViewById(R.id.name7);
        final EditText name8 = findViewById(R.id.name8);
        final EditText name9 = findViewById(R.id.name9);
        final EditText name10 = findViewById(R.id.name10);
        final EditText set1 = findViewById(R.id.set1);
        final EditText set2 = findViewById(R.id.set2);
        routineDB = initDB();
        initTableD();

        final Intent intent = getIntent();
        if (intent.getBooleanExtra("ex", false)) {
            name.setText(intent.getStringExtra("name"));
            String[] names=intent.getStringArrayExtra("names");
            name1.setText(names[0]);
            name2.setText(names[1]);
            name3.setText(names[2]);
            name4.setText(names[3]);
            name5.setText(names[4]);
            name6.setText(names[5]);
            name7.setText(names[6]);
            name8.setText(names[7]);
            name9.setText(names[8]);
            name10.setText(names[9]);
            set1.setText("" + intent.getIntExtra("set1", 0));
            set2.setText("" + intent.getIntExtra("set2", 0));
        }

        ImageButton saveB = findViewById(R.id.save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || set1.getText().toString().equals("") || set2.getText().toString().equals("") || name1.getText().toString().equals("") || name2.getText().toString().equals("")) {
                    Toast.makeText(Plus_D.this, "필수 내용을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {
                    if(name3.getText().toString().equals("")){
                        name3.setText("none");
                    }if(name4.getText().toString().equals("")){
                        name4.setText("none");
                    }if(name5.getText().toString().equals("")){
                        name5.setText("none");
                    }if(name6.getText().toString().equals("")){
                        name6.setText("none");
                    }if(name7.getText().toString().equals("")){
                        name7.setText("none");
                    }if(name8.getText().toString().equals("")){
                        name8.setText("none");
                    }if(name9.getText().toString().equals("")){
                        name9.setText("none");
                    }if(name10.getText().toString().equals("")){
                        name10.setText("none");
                    }
                    if (getIntent().getBooleanExtra("ex", false)) {
                        Intent intent=getIntent();
                        updateTableD(intent.getStringExtra("name"), name.getText().toString(),name1.getText().toString(),name2.getText().toString(),name3.getText().toString(),name4.getText().toString(),
                                name5.getText().toString(),name6.getText().toString(),name7.getText().toString(),name8.getText().toString(),name9.getText().toString(),name10.getText().toString(),
                                Integer.parseInt(set1.getText().toString()), Integer.parseInt(set2.getText().toString()));
                        Toast.makeText(Plus_D.this, "수정되었습니다", Toast.LENGTH_SHORT).show(); //수정
                    } else {
                        insertTableD(name.getText().toString(), name1.getText().toString(),name2.getText().toString(),name3.getText().toString(),name4.getText().toString(),
                                name5.getText().toString(),name6.getText().toString(),name7.getText().toString(),name8.getText().toString(),name9.getText().toString(),name10.getText().toString(),
                                Integer.parseInt(set1.getText().toString()), Integer.parseInt(set2.getText().toString()));
                        Toast.makeText(Plus_D.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ImageButton backB = findViewById(R.id.back);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {//called
        /*
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
        */

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
    }private void updateTableD(String original,String name,String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String name9,String name10,int set1,int set2){

        String sqlInsert = "UPDATE ROUTINE_D SET "+"name='"+name+"', name1='"+name1+"', name2='"+name2+"', name3='"+name3+"', name4='"+name4+"', name5='"+name5+"', name6='"+name6+"', name7='"+name7
                +"', name8='"+name8+"', name9='"+name9+"', name10='"+name10+"', set1="+set1+", set2="+set2+" WHERE name='"+original+"'";
        Log.d("bbbbbbbbbbbbbb",sqlInsert);
        routineDB.execSQL(sqlInsert);
    }
    private void initTableD(){
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_D (name TEXT,name1 TEXT,name2 TEXT,name3 TEXT,name4 TEXT,name5 TEXT,name6 TEXT,name7 TEXT,name8 TEXT,name9 TEXT,name10 TEXT,set1 INTEGER, set2 INTEGER)";
        routineDB.execSQL(sqlCreateTbl);
    }
    private void insertTableD(String name,String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String name9,String name10,int set1,int set2){//++","
        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_D (name ,name1 ,name2 ,name3 ,name4 ,name5 ,name6 ,name7 ,name8 ,name9 ,name10 ,set1 ,set2) " +
                "VALUES ('"+name+"','"+name1+"','"+name2+"','"+name3+"','"+name4+"','"+name5+"','"+name6+"','"+name7+"','"+name8+"','"+name9+"','"+name10+"',"+set1+","+set2+")";
        routineDB.execSQL(sqlInsert);
    }
}
