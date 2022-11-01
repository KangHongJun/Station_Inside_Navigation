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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import fragment.Fragment_InsideNavi;
import fragment.Fragment_InsideNavi_Song;
import fragment.Sadangb1;
import fragment.Sadangb3;

public class Inside_Navigation_test extends AppCompatActivity {
    TextView inside_station;
    static String curStation;
    Button inside_btn;

    int Step = 0;
    int stationnum = 0;
    int StartIn =0,EndIn=0;
    //층수 데이터
    int floor_cnt = 0;

    ListView listView_floor;
    ArrayAdapter<String> arrayAdapter;

    TextView inside_start,inside_arrival;


    Sadangb1 sadangb1;
    Sadangb3 sadangb3;
    Fragment_InsideNavi InsideB4,InsideB3,InsideB2,InsideB1;
    Fragment_InsideNavi_Song Inside_Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_navigation_test);

        inside_station = (TextView) findViewById(R.id.Inside_Station);

        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");
        //Toast.makeText(Inside_Navigation_test.this, curStation, Toast.LENGTH_SHORT).show();
        inside_station.setText(curStation);

        inside_start = findViewById(R.id.Inside_Start_Edit);
        inside_arrival = findViewById(R.id.Inside_Arrival_Edit);

        listView_floor = findViewById(R.id.Subway_Floor_List);

        inside_btn = findViewById(R.id.inside_Btn);


        sadangb1 = new Sadangb1();
        sadangb3 = new Sadangb3();
        //지도 프라그먼트
        InsideB1 = new Fragment_InsideNavi();
        InsideB2 = new Fragment_InsideNavi();
        InsideB3 = new Fragment_InsideNavi();
        InsideB4 = new Fragment_InsideNavi();
        Inside_Test = new Fragment_InsideNavi_Song();


        Toolbar toolbar = findViewById(R.id.Inside_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Inside_Navigation_test.this,"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        inside_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Arrival_Subway_Search.class);
                inside_launcher.launch(intent);
                Step = 0;//역 이름 다시입력하기위한 초기화 테스트

                Cursor cursor1 = sqlDB.rawQuery("select Max(B) from BeomB2_test3",null);

                cursor1.moveToLast();

                if(cursor1 != null){
                    floor_cnt = cursor1.getInt(0)/100;
                }

                Toast.makeText(Inside_Navigation_test.this, String.valueOf(floor_cnt), Toast.LENGTH_LONG).show();
            }
        });

        Cursor cursor1 = sqlDB.rawQuery("select Max(B) from BeomB2_test2",null);

        cursor1.moveToLast();

        if(cursor1 != null){
            floor_cnt = cursor1.getInt(0)/100;
        }

        //Toast.makeText(Inside_Navigation_test.this, String.valueOf(floor_cnt), Toast.LENGTH_LONG).show();

        inside_btn.setText("다음");
        inside_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재역 기준으로 이미지&버튼 세팅
               //스탭으로 구분
                if(Step==0){
                    if (inside_station.length()==0){
                        Toast.makeText(getApplicationContext(),"역 이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else{
                        try{
                            if (curStation.equals("범계")){
                                Bundle bundle = new Bundle();
                                bundle.putString("floor", "범계B2");
                                InsideB2.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,InsideB2).commit();
                            }
                            else if (curStation.equals("사당"))
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest, sadangb3).commit();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"다른 이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                        }
                        Step = 1;
                        Toast.makeText(getApplicationContext(),"현재 위치를 입력해주세요",Toast.LENGTH_SHORT).show();

                        //DB참조


                        //층수를 구별해야 하므로 전체조회 및 층수 저장
                       // Cursor cursor_inside = sqlDB.rawQuery("select * from b1",null);//db변경**
//                        while (cursor_inside.moveToNext()){
//                            insideG.input(cursor_inside.getInt(0),cursor_inside.getInt(1),cursor_inside.getInt(2));
//              insideG가 아닌 다른 배열에 저장
//                        }

                        //층수만큼 반복하여 층수*100으로 나눠서 각각 b1 b2 b3...에 저장후 각 길이를 구하고

                        //그래프
                        //insideG = new Graph(11);//전체길이로 그래프 생성 후 전체길이 만큼 반복하여 insideG에 값을 넣는다.


                        //insideG에 경로가 리턴되므로(노드 번호) 구간별로 나눈 후 아래의 단계에서 좌표값을 번들 데이터로 보내주던지 한다.
                        
                    }

                }else if(Step ==1){
                    if(!(inside_start.length()==0)){
                        Step = 2;

                        try{//일단 범계만 예시로
                            if (curStation.equals("범계")){
                                Bundle bundle = new Bundle();
                                bundle.putString("floor", "범계B1");
                                InsideB1.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,InsideB1).commit();
                            }

                            else if (curStation.equals("사당"))
                                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest, sadangb1).commit();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"다른 이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(getApplicationContext(),"목적지를 입력해주세요",Toast.LENGTH_SHORT).show();
                        inside_btn.setText("탐색");
                    }else{
                        Toast.makeText(getApplicationContext(),"현재 위치를 정확히 입력해주세요",Toast.LENGTH_SHORT).show();
                    }

                }else if(Step==2){
                    if(inside_arrival.length()==0){
                        Toast.makeText(getApplicationContext(),"목적지를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else{
                        //층수 리스트 생성
                        ListAdd(floor_cnt);

                        //위에서 목적지까지 입력하면 보이게
                        //B2.setVisibility(View.VISIBLE);
                        //B1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"아래의 버튼으로 층마다의 경로를 확인할수 있습니다.",Toast.LENGTH_SHORT).show();
                        stationnum=1;

                        if(stationnum==1){//프라그먼트에서 지도를 띄우기 위한 작업, 위에서 보낸 번들데이터를 여기에서 보내는게


                            Bundle bundle_last = new Bundle();
                            bundle_last.putInt("stationnum", stationnum);
                            bundle_last.putString("floor", "범계B2");
                            StartIn  = Integer.parseInt(String.valueOf(inside_start.getText()));
                            EndIn  = Integer.parseInt(String.valueOf(inside_arrival.getText()));
                            bundle_last.putInt("start",StartIn);
                            bundle_last.putInt("end",EndIn);
                            InsideB2.setArguments(bundle_last);
                            InsideB1.setArguments(bundle_last);
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,InsideB2).commit();




                    }
                }
            }
        });

        listView_floor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Inside_Test = new Fragment_InsideNavi_Song();
                String curFloor = adapterView.getItemAtPosition(i).toString();

                String sql = "Select * from Floor_TB Where Station_Name = '" + curStation + "' AND Floor_Nm = '" + curFloor + "'";
                Cursor Floor_cursor = sqlDB.rawQuery(sql,null);

                Floor_cursor.moveToLast();

                //String TEST = Floor_cursor.getString(0).concat(Floor_cursor.getString(1));

                //Toast.makeText(Inside_Navigation_test.this, curFloor, Toast.LENGTH_LONG).show();
                System.out.println(curFloor);

                Bundle bundle = new Bundle();
                bundle.putString("Name", curStation);
                bundle.putString("Floor", curFloor);
                bundle.putInt("stationnum", stationnum);

                StartIn  = Integer.parseInt(String.valueOf(inside_start.getText()));
                EndIn  = Integer.parseInt(String.valueOf(inside_arrival.getText()));

                bundle.putInt("start",StartIn);
                bundle.putInt("end",EndIn);

                Inside_Test.setArguments(bundle);

                //System.out.println("Check");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,Inside_Test).commitAllowingStateLoss();
                //finish();
            }
        });

        /*B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationnum = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("stationnum", stationnum);
                bundle.putString("floor", "범계B2");

                InsideB2.setArguments(bundle);
                try {
                    if (curStation.equals("범계"))
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,InsideB2).commit();
                    else if (curStation.equals("사당")){
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,sadangb3).commit();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"다른역을 입력 해주세요",Toast.LENGTH_SHORT).show();
                }


            }
        });

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationnum = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("stationnum", stationnum);
                bundle.putString("floor", "범계B1");
                StartIn  = Integer.parseInt(String.valueOf(inside_start.getText()));
                EndIn  = Integer.parseInt(String.valueOf(inside_arrival.getText()));
                bundle.putInt("start",StartIn);
                bundle.putInt("end",EndIn);

                InsideB1.setArguments(bundle);

                try {
                    if (curStation.equals("범계"))
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,InsideB1).commit();
                    else if (curStation.equals("사당")){
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,sadangb1).commit();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"다른역을 입력 해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    }

    private void ListAdd(int Cnt){
        ArrayList<String> itemList = new ArrayList<>();

        for(int i=1; i <= Cnt; i++){
            itemList.add("B".concat(String.valueOf(i)));
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
        listView_floor.setAdapter(arrayAdapter);
    }

    ActivityResultLauncher<Intent> inside_launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult data) {
                    if (data.getResultCode() == Activity.RESULT_OK){
                        Intent intent = data.getData();
                        String result = intent.getStringExtra("result");

                        curStation = result;
                        inside_station.setText(result);


                    }
                }
            });

    public void setStartText(String num){
        inside_start.setText(num);
    }

    public void setStartSubText(String num){
        inside_arrival.setText(num);
    }



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
                Intent intent = new Intent(Inside_Navigation_test.this,Subway_Map.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_detail:{
                Intent intent = new Intent(Inside_Navigation_test.this,Subway_Detailed_View.class);
                intent.putExtra("station",inside_station.getText().toString());
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
