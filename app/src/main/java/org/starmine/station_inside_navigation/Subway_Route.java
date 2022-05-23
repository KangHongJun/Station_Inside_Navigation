package org.starmine.station_inside_navigation;

import android.app.Activity;
import android.content.Intent;
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

public class Subway_Route extends AppCompatActivity {
    private static String curStation;
    TextView start_station;
    TextView arrival_station;

    Button search_btn;
    //EditText start_station, arrival_station;
    String start, arrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_route);

        start_station = (TextView) findViewById(R.id.Route_Start_Edit);
        arrival_station = (TextView) findViewById(R.id.Route_Arrival_Edit);


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
