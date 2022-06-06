package org.starmine.station_inside_navigation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Inside_Navigation extends AppCompatActivity {
    private static String curStation;
    TextView inside_station;
    Button inside_search;

    static Graph insideG;
    TextView inside_start,inside_arrival, inside_route;

    int inside_distance;
    String route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_navigation);

        //그래프 생성 임시 - 나중엔 다른 방식으로
        insideG = new Graph(11);
        //노드값 입력
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Inside_Navigation.this,"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);
        Cursor cursor_inside = sqlDB.rawQuery("select * from namtest",null);
        while (cursor_inside.moveToNext()){
            insideG.input(cursor_inside.getInt(0),cursor_inside.getInt(1),cursor_inside.getInt(2));
        }//



        inside_route = findViewById(R.id.Inside_Route_Text);
        inside_station = (TextView) findViewById(R.id.Inside_Station);

        inside_start = findViewById(R.id.Inside_Start_Edit);
        inside_arrival = findViewById(R.id.Inside_Arrival_Edit);
        inside_search = findViewById(R.id.Inside_Search_Btn);

        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");
        inside_station.setText(curStation);

        Toolbar toolbar = findViewById(R.id.Inside_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        inside_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Arrival_Subway_Search.class);

                inside_launcher.launch(intent);
            }
        });

        inside_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route = "";
                //입력되는 번호는 1~11, 노드번호는 0~10
                int start = Integer.parseInt(inside_start.getText().toString())-1;
                int arrival = Integer.parseInt(inside_arrival.getText().toString())-1;

                inside_distance = insideG.dijkstra(start,arrival);



                for (int i=Graph.route.length-1;i>=0;i--){
                    if (Graph.route[i]==0){
                        System.out.println(Graph.route[i]+"0일떄 i는 "+i);
                    }else{
                        route = route +Graph.route[i] + " 번으로 이동"+"\n";

                        System.out.println(Graph.route[i]+"s");

                    }
                }
                route = route +inside_arrival.getText()+"번 목적지로 이동";
                inside_route.setText("약 "+inside_distance+"m"+"\n"+route);
            }
        });


    }

    ActivityResultLauncher<Intent> inside_launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult data) {
                    if (data.getResultCode() == Activity.RESULT_OK){
                        Intent intent = data.getData();
                        String result = intent.getStringExtra("result");

                        inside_station.setText(result);
                    }
                }
            });

//4번 계단까지 약 n미터 이동하여 2층으로 이동
//6번 계단까지 약n미터 이동하여 3층으로 이동
//출구 1번까지 약 n미터 이동
    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.inside_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
            case R.id.menu_map:{
                Intent intent = new Intent(Inside_Navigation.this,Subway_Map.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_detail:{
                Intent intent = new Intent(Inside_Navigation.this,Subway_Detailed_View.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
