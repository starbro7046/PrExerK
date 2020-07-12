package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class SettingDy extends AppCompatActivity {

    public SQLiteDatabase routineDB;
    ListView listView;
    ExDAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_dy);

        routineDB=initDB();
        initTableD();



        //updateListView();

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("bbbbbbbbbbbbbb","ff");
            }
        });

         */

        Button plsB=findViewById(R.id.plusD);
        plsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tranSet = new Intent(SettingDy.this, Plus_D.class);
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
            ArrayList data=new ArrayList<Data_D>();

            String sqlQuerySel = "SELECT * FROM ROUTINE_D";
            Cursor cursor=routineDB.rawQuery(sqlQuerySel,null);
            if(cursor.moveToFirst()&&cursor!=null){
                do { //사용자 편의성을 위해 검사코드는 넣지 않음, 공백이 있어도 운동 화면에서 공백처리
                    String [] names={cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10)};
                    data.add(new Data_D(cursor.getString(0),names,cursor.getInt(11),cursor.getInt(12)));
                }while (cursor.moveToNext());
            }
            return data;
        }
        return null;
    }public void updateListView(){
        listView=findViewById(R.id.listView);
        arrayAdapter=new ExDAdapter(this,R.layout.list_item,getList());
        listView.setAdapter(arrayAdapter);
    }
    private SQLiteDatabase initDB(){
        File file= new File(getFilesDir(),"routine.db");
        SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(file,null);
        if (db == null) {
            Log.d("tttttt","failed to create db" + file.getAbsolutePath()) ;
        }
        return db;
    }
    private void initTableD(){
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_D (name TEXT,name1 TEXT,name2 TEXT,name3 TEXT,name4 TEXT,name5 TEXT,name6 TEXT,name7 TEXT,name8 TEXT,name9 TEXT,name10 TEXT,set1 INTEGER, set2 INTEGER)";
        routineDB.execSQL(sqlCreateTbl);
    }
    private void insertTableD(String name,String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String name9,String name10,int set1,int set2){//++","
        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_D (name ,name1 ,name2 ,name3 ,name4 ,name5 ,name6 ,name7 ,name8 ,name9 ,name10 ,set1 ,set2) " +
                "VALUES ('"+name+"','"+name1+"','"+name2+"','"+name3+"','"+name4+"','"+name5+"','"+name6+"','"+name7+"','"+name8+"','"+name9+"','"+name10+"',"+set1+","+set2+")";
        routineDB.execSQL(sqlInsert);
    }public void deleteData(String name){
        String sqlInsert = "DELETE FROM ROUTINE_D WHERE name='"+name+"'";
        routineDB.execSQL(sqlInsert);
    }
}
