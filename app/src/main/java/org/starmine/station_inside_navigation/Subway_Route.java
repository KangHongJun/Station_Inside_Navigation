package org.starmine.station_inside_navigation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fragment.Fragment_Route;

public class Subway_Route extends AppCompatActivity {
    private static String curStation;
    TextView start_station;
    TextView arrival_station;
    Fragment_Route fragment_route;

    Button search_btn;
    //EditText start_station, arrival_station;
    String start, arrival;
    static int startV, endV;
    public static String[] subway_route;
    static String route_str;
    static int visit_station;

    static int route_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_route);

        fragment_route = new Fragment_Route();

        start_station = (TextView) findViewById(R.id.Route_Start_Edit);
        arrival_station = (TextView) findViewById(R.id.Route_Arrival_Edit);

        search_btn = findViewById(R.id.Route_Search_Btn);

        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");
        start_station.setText(curStation);

        //start_station.setOnClickListener();
        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Route_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //루트 검색
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subway_route = new String[100];
                visit_station = 0;

                //시작 도착역 텍스트 가져오기
                start = String.valueOf(start_station.getText());
                arrival = String.valueOf(arrival_station.getText());
                //Toast.makeText(getApplicationContext(),start+arrival,Toast.LENGTH_SHORT).show();

                //subway_coordinate 역이름에 해당하는 코드번호 가져오기
                Cursor route_name = Subway_Map.cursor_coor;
                route_name.moveToFirst();
                while (route_name.moveToNext()){
                    if(start.equals(route_name.getString(1))){
                        int line = route_name.getInt(0)/100;
                        System.out.println(start+"start : "+route_name.getInt(0)+line);

                        //start, arrival 코드값 조정
                        if(line==1){
                            startV = route_name.getInt(0)-100;
                        }else if(line==2){
                            startV = route_name.getInt(0)-124;
                        }else if(line==3){
                            startV = route_name.getInt(0)-181;
                        }else if(line==4){
                            startV = route_name.getInt(0)-237;
                        }else if(line==5){
                            startV = route_name.getInt(0)-285;
                        }else if(line==6){
                            startV = route_name.getInt(0)-330;
                        }else if(line==7){
                            startV = route_name.getInt(0)-391;
                        }else if(line==8){
                            startV = route_name.getInt(0)-437;
                        }else if(line==9){
                            startV = route_name.getInt(0)-519;
                        }
                        break;
                    }
                }

                //
                route_name.moveToFirst();
                while (route_name.moveToNext()){
                    if(arrival.equals(route_name.getString(1))){
                        int line = route_name.getInt(0)/100;
                        System.out.println(arrival+"end : "+route_name.getInt(0)+line);

                        //start, arrival 코드값 조정
                        if(line==1){
                            endV = route_name.getInt(0)-100;
                        }else if(line==2){
                            endV = route_name.getInt(0)-124;
                        }else if(line==3){
                            endV = route_name.getInt(0)-181;
                        }else if(line==4){
                            endV = route_name.getInt(0)-237;
                        }else if(line==5){
                            endV = route_name.getInt(0)-285;
                        }else if(line==6){
                            endV = route_name.getInt(0)-330;
                        }else if(line==7){
                            endV = route_name.getInt(0)-391;
                        }else if(line==8){
                            endV = route_name.getInt(0)-437;
                        }else if(line==9){
                            endV = route_name.getInt(0)-519;
                        }
                        break;
                    }
                }



                //Toast.makeText(getApplicationContext(),route_name.getString(0)+"",Toast.LENGTH_SHORT).show();


                //MainActivity에서 그래프 미리 생성해두고 검색하기
                if(startV != endV){

                    route_time = MainActivity.g.dijkstra(startV,endV);


                    int visit = 0;

                    for (int j=0;j<Graph.route.length-1;j++){
                        if (Graph.route[j]==0){
                            break;
                        }else if(Graph.route[j]<=76){
                            visit =Graph.route[j]+100;
                        }else if(Graph.route[j]>=77 && Graph.route[j]<=119){
                            visit =Graph.route[j]+124;
                        }else if(Graph.route[j]>=120 && Graph.route[j]<=163){
                            visit =Graph.route[j]+181;
                        }else if(Graph.route[j]>=164 && Graph.route[j]<=214){
                            visit =Graph.route[j]+237;
                        }else if(Graph.route[j]>=215 && Graph.route[j]<=270){
                            visit =Graph.route[j]+285;
                        }else if(Graph.route[j]>=271 && Graph.route[j]<=309){
                            visit =Graph.route[j]+330;
                        }else if(Graph.route[j]>=310 && Graph.route[j]<=363){
                            visit =Graph.route[j]+391;
                        }else if(Graph.route[j]>=364 && Graph.route[j]<=381){
                            visit =Graph.route[j]+437;
                        }else if(Graph.route[j]>=382 && Graph.route[j]<=419){
                            visit =Graph.route[j]+519;
                        }

                        route_name.moveToFirst();
                        while (route_name.moveToNext()){
                            if(visit==route_name.getInt(0)){
                                subway_route[visit_station]=route_name.getString(1);
                                System.out.println(route_name.getString(1) + "방문");
                                visit_station++;
                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "같은 역 입력됨", Toast.LENGTH_SHORT).show();
                }
                //루트 정보, 프라그먼트 설정
                Bundle route_bundle = new Bundle();
                route_bundle.putStringArray("subway_route", subway_route);
                route_bundle.putInt("time", route_time);
                fragment_route.setArguments(route_bundle);



                getSupportFragmentManager().beginTransaction().replace(R.id.Route_Container,fragment_route).commit();
            }
        });

        start_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext() , Start_Subway_Search.class);

                start_launcher.launch(intent);
            }
        });

        arrival_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Arrival_Subway_Search.class);

                arrival_launcher.launch(intent);
            }
        });
    }
    private void setFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.Route_Container,fragment_route).commit();
    }

    ActivityResultLauncher<Intent> start_launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult data) {
                    if (data.getResultCode() == Activity.RESULT_OK){
                        Intent intent = data.getData();
                        String result = intent.getStringExtra("result");

                        start_station.setText(result);
                    }
                }
            });

    ActivityResultLauncher<Intent> arrival_launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult data) {
                    if (data.getResultCode() == Activity.RESULT_OK){
                        Intent intent = data.getData();
                        String result = intent.getStringExtra("result");

                        arrival_station.setText(result);
                    }
                }
            });

    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.route_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //메뉴 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }

            case R.id.menu_change: {
                String temp;
                temp = start_station.getText().toString();
                //Toast.makeText(Subway_Route.this, temp , Toast.LENGTH_LONG).show();
                start_station.setText(arrival_station.getText().toString());
                arrival_station.setText(temp);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
