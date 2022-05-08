package org.starmine.station_inside_navigation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import fragment.Fragment_Holiday;
import fragment.Fragment_Saturday;
import fragment.Fragment_Weekday;

//지하철 시간표
public class Subway_Schedule extends AppCompatActivity {

    TextView Station_name;
    static String curStation;


    Fragment_Weekday Weekday;
    Fragment_Saturday Saturday;
    Fragment_Holiday Holiday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_schedule);

        setDirection();

        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");

        Station_name = findViewById(R.id.Schedule_Station_Text);
        Station_name.setText(curStation+"호선");

        Weekday = new Fragment_Weekday();
        Saturday = new Fragment_Saturday();
        Holiday = new Fragment_Holiday();



        Bundle bundle = new Bundle();
        bundle.putString("station", curStation);
        Weekday.setArguments(bundle);
        Saturday.setArguments(bundle);
        Holiday.setArguments(bundle);


        getSupportFragmentManager().beginTransaction().replace(R.id.Schedule_Container,Weekday).commit();


        //라디오 버튼
        RadioGroup radioGroup = findViewById(R.id.Schedule_RadioGroup);
        radioGroup.check(R.id.Week_radioButton);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Fragment selected = null;
                switch (i){
                    case R.id.Week_radioButton:
                        selected=Weekday;
                        break;
                    case R.id.Satur_radioButton:
                        selected =Saturday;
                        break;
                    case R.id.Hoil_radioButton:
                        selected=Holiday;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.Schedule_Container,selected).commit();
            }
        });
    }
    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.schedule_menu,menu);

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
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDirection(){
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getApplicationContext(),"subway_schedule.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);


        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");

        String sqlCode = "select DIR from line4 where NAME = " +"\""+ curStation +"\"";
        Cursor UPDIR_cursor = sqlDB.rawQuery(sqlCode,null);

        int count = UPDIR_cursor.getCount();
        String[] array = new String[100];

        UPDIR_cursor.moveToFirst();
        for(int i = 0; i < count; i++){
            array[i] = UPDIR_cursor.getString(0);
            UPDIR_cursor.moveToNext();
        }

        Toast.makeText(getApplicationContext(),""+count+curStation,Toast.LENGTH_SHORT).show();

        TextView LDirText = findViewById(R.id.Schedule_LDirection_Text);
        LDirText.setText(array[0] + "방향");

        TextView RDirText = findViewById(R.id.Schedule_RDirection_Text);
        RDirText.setText(array[1] + "방향");

    }
}
