package org.starmine.station_inside_navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_navigation);

        inside_station = (TextView) findViewById(R.id.Inside_Station);

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
