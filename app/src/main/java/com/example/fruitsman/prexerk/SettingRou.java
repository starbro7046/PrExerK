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

public class SettingRou extends AppCompatActivity {

    public SQLiteDatabase routineDB;
    ListView listView;
    ExRAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_rou);

        routineDB = initDB();
        initTableR();


        //updateListView();

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("bbbbbbbbbbbbbb","ff");
            }
        });

         */

        Button plsB = findViewById(R.id.plusD);
        plsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tranSet = new Intent(SettingRou.this, Plus_R.class);
                startActivity(tranSet);
            }
        });


    }

    @Override
    protected void onResume() {
        updateListView();
        super.onResume();
    }

    public ArrayList getList() {
        if (routineDB != null) {
            ArrayList data = new ArrayList<Data_R>();

            String sqlQuerySel = "SELECT * FROM ROUTINE_R";
            Cursor cursor = routineDB.rawQuery(sqlQuerySel, null);
            if (cursor.moveToFirst() && cursor != null) {
                do { //사용자 편의성을 위해 검사코드는 넣지 않음, 공백이 있어도 운동 화면에서 공백처리
                    String[] names = {cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10)};
                    data.add(new Data_R(cursor.getString(0), names));
                } while (cursor.moveToNext());
            }
            return data;
        }
        return null;
    }

    public void updateListView() {
        listView = findViewById(R.id.listView);
        arrayAdapter = new ExRAdapter(this, R.layout.list_item, getList());
        listView.setAdapter(arrayAdapter);
    }
    private SQLiteDatabase initDB() {
        File file = new File(getFilesDir(), "routine.db");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
        if (db == null) {
            Log.d("tttttt", "failed to create db" + file.getAbsolutePath());
        }
        return db;
    }private String loadValuesR(String name,int index){
        if(routineDB!=null)
        {
            String sqlQuerySel = "SELECT * FROM ROUTINE_R WHERE name='"+name+"'";
            Cursor cursor=null;
            cursor=routineDB.rawQuery(sqlQuerySel,null);
            if(cursor.moveToNext()) {
                return cursor.getString(index);
            }
        }
        return "none";
    }
    private void initTableR(){
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_R (name TEXT,ex1 TEXT,ex2 TEXT,ex3 TEXT,ex4 TEXT,ex5 TEXT,ex6 TEXT,ex7 TEXT,ex8 TEXT,ex9 TEXT,ex10 TEXT)";
        routineDB.execSQL(sqlCreateTbl);
    }private void insertTableR(String name,String ex1,String ex2 ,String ex3,String ex4,String ex5,String ex6,String ex7,String ex8, String ex9, String ex10){
        String sqlInsert = "INSERT OR REPLACE INTO ROUTINE_R (name,ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9,ex10) " +
                "VALUES ('"+name+"','"+ex1+"','"+ex2+"','"+ex3+"','"+ex4+"','"+ex5+"','"+ex6+"','"+ex7+"','"+ex8+"','"+ex9+"','"+ex10+"')";
        routineDB.execSQL(sqlInsert);
    }public void deleteData(String name){
    String sqlInsert = "DELETE FROM ROUTINE_R WHERE name='"+name+"'";
    routineDB.execSQL(sqlInsert);
}
}