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

public class Plus_R extends AppCompatActivity {
    public  SQLiteDatabase routineDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus__r);


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

        routineDB = initDB();
        initTableR();

        final Intent intent = getIntent();
        if (intent.getBooleanExtra("ex", false)) {
            name.setText(intent.getStringExtra("name"));
            String[] exs=intent.getStringArrayExtra("exs");
            name1.setText(exs[0]);
            name2.setText(exs[1]);
            name3.setText(exs[2]);
            name4.setText(exs[3]);
            name5.setText(exs[4]);
            name6.setText(exs[5]);
            name7.setText(exs[6]);
            name8.setText(exs[7]);
            name9.setText(exs[8]);
            name10.setText(exs[9]);

        }

        ImageButton saveB = findViewById(R.id.save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || name1.getText().toString().equals("")) {
                    Toast.makeText(Plus_R.this, "필수 내용을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {
                    if(name2.getText().toString().equals("")){
                        name2.setText("none");
                    }if(name3.getText().toString().equals("")){
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
                        updateTableR(intent.getStringExtra("name"), name.getText().toString(),name1.getText().toString(),name2.getText().toString(),name3.getText().toString(),name4.getText().toString(),
                                name5.getText().toString(),name6.getText().toString(),name7.getText().toString(),name8.getText().toString(),name9.getText().toString(),name10.getText().toString());
                        Toast.makeText(Plus_R.this, "수정되었습니다", Toast.LENGTH_SHORT).show(); //수정
                    } else {
                        insertTableR(name.getText().toString(), name1.getText().toString(),name2.getText().toString(),name3.getText().toString(),name4.getText().toString(),
                                name5.getText().toString(),name6.getText().toString(),name7.getText().toString(),name8.getText().toString(),name9.getText().toString(),name10.getText().toString());
                        Toast.makeText(Plus_R.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
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
    private SQLiteDatabase initDB(){
        File file= new File(getFilesDir(),"routine.db");
        SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(file,null);
        if (db == null) {
            Log.d("tttttt","failed to create db" + file.getAbsolutePath()) ;
        }
        return db;
    }private void updateTableR(String original,String name,String ex1,String ex2,String ex3,String ex4,String ex5,String ex6,String ex7,String ex8,String ex9,String ex10){

        String sqlInsert = "UPDATE ROUTINE_R SET "+"name='"+name+"', ex1='"+ex1+"', ex2='"+ex2+"', ex3='"+ex3+"', ex4='"+ex4+"', ex5='"+ex5+"', ex6='"+ex6+"', ex7='"+ex7
                +"', ex8='"+ex8+"', ex9='"+ex9+"', ex10='"+ex10+"' WHERE name='"+original+"'";
        Log.d("bbbbbbbbbbbbbb",sqlInsert);
        routineDB.execSQL(sqlInsert);
    }
    private void initTableR(){
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_R (name TEXT,ex1 TEXT,ex2 TEXT,ex3 TEXT,ex4 TEXT,ex5 TEXT,ex6 TEXT,ex7 TEXT,ex8 TEXT,ex9 TEXT,ex10 TEXT)";
        routineDB.execSQL(sqlCreateTbl);
    }
    private void insertTableR(String name,String ex1,String ex2 ,String ex3,String ex4,String ex5,String ex6,String ex7,String ex8, String ex9, String ex10){
        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_R (name,ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10) " +
                "VALUES ('"+name+"','"+ex1+"','"+ex2+"','"+ex3+"','"+ex4+"','"+ex5+"','"+ex6+"','"+ex7+"','"+ex8+"','"+ex9+"','"+ex10+"')";
        routineDB.execSQL(sqlInsert);
    }
}