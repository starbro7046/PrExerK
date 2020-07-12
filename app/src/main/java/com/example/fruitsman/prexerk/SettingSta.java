package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingSta extends AppCompatActivity {

    public SQLiteDatabase routineDB;
    ListView listView;
    ExKAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_sta);

        routineDB=initDB();
        initTableK();

        //updateListView();

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("bbbbbbbbbbbbbb","ff");
            }
        });

         */

        Button plsB=findViewById(R.id.plus);
        plsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tranSet = new Intent(SettingSta.this, Plus_K.class);
                startActivity(tranSet);
            }
        });


    }
    @Override
    protected void onResume() {
        updateListView();
        super.onResume();
    }

    public ArrayList getList(){
        if(routineDB!=null)
        {
            ArrayList data=new ArrayList<Data_K>();

            String sqlQuerySel = "SELECT * FROM ROUTINE_K";
            Cursor cursor=routineDB.rawQuery(sqlQuerySel,null);
            if(cursor.moveToFirst()&&cursor!=null){
                do {

                    data.add(new Data_K(cursor.getString(0),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),
                            cursor.getInt(9),cursor.getInt(10),cursor.getInt(11)));
                }while (cursor.moveToNext());
            }
            return data;
        }
        return null;
    }public void updateListView(){
        listView=findViewById(R.id.listView);
         arrayAdapter=new ExKAdapter(this,R.layout.list_item,getList());
        listView.setAdapter(arrayAdapter);
    }
    private String loadValuesK(String name,int index1){
        if(routineDB!=null)
        {
            String sqlQuerySel = "SELECT * FROM ROUTINE_K WHERE name='"+name+"'";
            Cursor cursor=routineDB.rawQuery(sqlQuerySel,null);
            if(cursor.moveToNext()){
                return cursor.getString(index1);
            }else{
                return "n";
            }
        }
        return "none";
    }private void initTableK(){//fix later
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_K (name TEXT,pose INTEGER,left1 INTEGER,left2 INTEGER,right1 INTEGER,right2 INTEGER,left3 INTEGER,left4 INTEGER,right3 INTEGER,right4 INTEGER,time INTEGER,accuracy INTEGER)";
        routineDB.execSQL(sqlCreateTbl);
    }private void insertTableK(String name,int pose,int left1,int left2,int right1,int right2,int left3,int left4,int right3,int right4,int time,int accuracy){

        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_K (name,pose,left1,left2,right1,right2,left3,left4,right3,right4,time,accuracy) " +
                "VALUES ("+"'"+name+"'"+","+pose+","+left1+","+left2+","+right1+","+right2+","+left3+","+left4+","+right3+","+right4+","+time+","+accuracy+")";
        routineDB.execSQL(sqlInsert);
    }
    private SQLiteDatabase initDB(){
        File file= new File(getFilesDir(),"routine.db");
        SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(file,null);
        if (db == null) {
            Log.d("tttttt","failed to create db" + file.getAbsolutePath()) ;
        }
        return db;
    }
    private void insertTableD(String name,String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String name9,String name10,int set1,int set2){//++","
        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_D (name ,name1 ,name2 ,name3 ,name4 ,name5 ,name6 ,name7 ,name8 ,name9 ,name10 ,set1 ,set2) " +
                "VALUES ('"+name+"','"+name1+"','"+name2+"','"+name3+"','"+name4+"','"+name5+"','"+name6+"','"+name7+"','"+name8+"','"+name9+"','"+name10+"',"+set1+","+set2+")";
        routineDB.execSQL(sqlInsert);
    }public void deleteData(String name){
        String sqlInsert = "DELETE FROM ROUTINE_K WHERE name='"+name+"'";
        routineDB.execSQL(sqlInsert);
    }
}
