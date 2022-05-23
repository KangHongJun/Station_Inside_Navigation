package org.starmine.station_inside_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Subway_Route extends AppCompatActivity {
    private static String curStation;

    Button search_btn;
    EditText start_station, arrival_station;
    String start, arrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_route);
        start_station = (EditText)findViewById(R.id.Route_Start_Edit);
        arrival_station = (EditText)findViewById(R.id.Route_Arrival_Edit);
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
                start = String.valueOf(start_station.getText());
                arrival = String.valueOf(arrival_station.getText());
                Toast.makeText(getApplicationContext(),start+arrival,Toast.LENGTH_SHORT).show();
            }
        });







    }

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
        }
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_change:
                Toast.makeText(this,"설정메뉴",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
