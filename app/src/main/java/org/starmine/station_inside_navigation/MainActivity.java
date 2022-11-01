package org.starmine.station_inside_navigation;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    static Graph g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //역간 거리 데이터 읽기
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(MainActivity.this,"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        Cursor cursor_time = sqlDB.rawQuery("select * from subway_time2",null);
        //그래프 생성 및 입력
        g = new Graph(420);
        while (cursor_time.moveToNext()){
            g.input(cursor_time.getInt(0),cursor_time.getInt(1),cursor_time.getInt(2));
        }

        //환승
        DBHelper Helper2;
        SQLiteDatabase sqlDB2;
        Helper2 = new DBHelper(MainActivity.this,"subway_info.db",null,1);
        sqlDB2 = Helper2.getReadableDatabase();
        Helper2.onCreate(sqlDB2);

        Cursor cursor_trans = sqlDB.rawQuery("select * from subway_transfer",null);
        //그래프 생성 및 입력
        while (cursor_trans.moveToNext()){
            g.input(cursor_trans.getInt(0),cursor_trans.getInt(1),cursor_trans.getInt(2));
        }



        //g.dijkstra(118,37);


        Button Main_Route_Btn = (Button) findViewById(R.id.Main_Route_Btn);
        Main_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Map.class);
                startActivity(intent);
            }
        });

        //내부안내 인텐트
        Button Main_Guide_Btn = (Button) findViewById(R.id.Main_Guide_Btn);
        Main_Guide_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //잠시 테스트로
                Intent intent = new Intent(getApplicationContext(), Inside_Navigation.class);
                startActivity(intent);
            }
        });
    }
}