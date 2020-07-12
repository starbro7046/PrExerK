package com.example.fruitsman.prexerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SeleRou extends AppCompatActivity {

    public SQLiteDatabase routineDB;
    ListView listView;
    ExRAdapterS arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sele_rou);

        routineDB = initDB();
        initTableR();
        updateListView();
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
    }public void seleData(String name){
        SharedPreferences sharedPreferences=getSharedPreferences("settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("routineName",name);
        editor.commit();
        Toast.makeText(SeleRou.this, "선택되었습니다.", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void updateListView() {
        listView = findViewById(R.id.listView);
        arrayAdapter = new ExRAdapterS(this, R.layout.list_view, getList());
        listView.setAdapter(arrayAdapter);
    }
    private SQLiteDatabase initDB() {
        File file = new File(getFilesDir(), "routine.db");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
        if (db == null) {
            Log.d("tttttt", "failed to create db" + file.getAbsolutePath());
        }
        return db;
    }
    private void initTableR(){
        String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ROUTINE_R (name TEXT,ex1 TEXT,ex2 TEXT,ex3 TEXT,ex4 TEXT,ex5 TEXT,ex6 TEXT,ex7 TEXT,ex8 TEXT,ex9 TEXT,ex10 TEXT)";
        routineDB.execSQL(sqlCreateTbl);
    }
}
