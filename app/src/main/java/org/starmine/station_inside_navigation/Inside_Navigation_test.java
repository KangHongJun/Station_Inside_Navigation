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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fragment.Beomgyeb1;
import fragment.Beomgyeb2;

public class Inside_Navigation_test extends AppCompatActivity {
    TextView inside_station;
    Button inside_btn;
    Button B2, B1;

    int Step = 0;
    int stationnum = 0;

    TextView inside_start,inside_startSub,inside_arrival;

    Beomgyeb2 BeomgyeB2;
    Beomgyeb1 BeomgyeB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_navigation_test);

        inside_station = (TextView) findViewById(R.id.Inside_Station);

        inside_start = findViewById(R.id.Inside_Start_Edit);
        inside_startSub = findViewById(R.id.Inside_StartSub_Edit);

        inside_arrival = findViewById(R.id.Inside_Arrival_Edit);

        inside_btn = findViewById(R.id.inside_Btn);


        B2 = findViewById(R.id.Inside_B2_Btn);
        B1 = findViewById(R.id.Inside_B1_Btn);

        B2.setVisibility(View.INVISIBLE);
        B1.setVisibility(View.INVISIBLE);



        BeomgyeB2 = new Beomgyeb2();
        BeomgyeB1 = new Beomgyeb1();

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

        inside_btn.setText("??????");
        inside_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //???????????? ??????

                if(Step==0){
                    if (inside_station.length()==0){
                        Toast.makeText(getApplicationContext(),"??? ????????? ??????????????????",Toast.LENGTH_SHORT).show();
                    }else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,BeomgyeB2).commit();

                        Step = 1;
                        Toast.makeText(getApplicationContext(),"?????? ????????? ??????????????????",Toast.LENGTH_SHORT).show();
                    }

                }else if(Step ==1){
                    if(!(inside_start.length()==0|inside_startSub.length()==0)){
                        Step = 2;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,BeomgyeB1).commit();
                        Toast.makeText(getApplicationContext(),"???????????? ??????????????????",Toast.LENGTH_SHORT).show();
                        inside_btn.setText("??????");
                    }else{
                        Toast.makeText(getApplicationContext(),"?????? ????????? ????????? ??????????????????",Toast.LENGTH_SHORT).show();
                    }

                }else if(Step==2){
                    if(inside_arrival.length()==0){
                        Toast.makeText(getApplicationContext(),"???????????? ??????????????????",Toast.LENGTH_SHORT).show();
                    }else{
                        B2.setVisibility(View.VISIBLE);
                        B1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"????????? ???????????? ???????????? ????????? ???????????? ????????????.",Toast.LENGTH_SHORT).show();
                        stationnum=1;

                        if(stationnum==1){
                            Bundle bundle = new Bundle();
                            bundle.putInt("stationnum", stationnum);
                            BeomgyeB2.setArguments(bundle);
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,BeomgyeB2).commit();






                        Beomgyeb1 b1 = (Beomgyeb1) getSupportFragmentManager().findFragmentById(R.id.container_insidetest);
                        b1.setstairColor2();


                    }
                }
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,BeomgyeB2).commit();
            }
        });

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationnum = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("stationnum", stationnum);
                BeomgyeB1.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.container_insidetest,BeomgyeB1).commit();
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

    public void setStartText(String num){
        inside_start.setText(num);
    }

    public void setStartSubText(String num){
        inside_arrival.setText(num);
    }



    //?????? ??????
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.inside_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar??? back??? ????????? ??? ??????
                // ???????????? ??????
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
