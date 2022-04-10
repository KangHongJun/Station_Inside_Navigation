package org.starmine.station_inside_navigation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import fragment.Fragment_Detail;


public class Subway_Detailed_View extends AppCompatActivity {

    Fragment_Detail detailed_view;

    private static Cursor cursor_line;
    private static String curStation;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_detailed_view);

        //DB class따로 만들기
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Detailed_View.this, "subway_info.db", null, 1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);


        //intent로 받은 지하철이름에 해당하는  값 조회
        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");


        String sql_line = "select * from subway_line where NAME = " + "\"" + curStation + "\"" + "";
        cursor_line = sqlDB.rawQuery(sql_line, null);
        int count = cursor_line.getCount();

        String[] Staionline = new String[10];//역 호선 정보

        for (int i = 0; i < count; i++) {
            cursor_line.moveToNext();
            Staionline[i] = cursor_line.getString(3);
            Toast.makeText(getApplicationContext(), ""+Staionline[i], Toast.LENGTH_LONG).show();
        }

        detailed_view = new Fragment_Detail();


        //프라크먼트 데이터 이동동
        Bundle bundle = new Bundle();
        bundle.putString("station", curStation);
        detailed_view.setArguments(bundle);

        //

        TabLayout tabs = findViewById(R.id.Detail_subway_line_Tab);
        tabs.addTab(tabs.newTab().setText("1"));
        tabs.addTab(tabs.newTab().setText("2"));
        tabs.addTab(tabs.newTab().setText("3"));
        tabs.addTab(tabs.newTab().setText("4"));
        tabs.addTab(tabs.newTab().setText("5"));
        tabs.addTab(tabs.newTab().setText("6"));
        tabs.addTab(tabs.newTab().setText("7"));
        tabs.addTab(tabs.newTab().setText("8"));

        //탭 숨기기 gone
        int tab_count = tabs.getTabCount();
        for (int i = 0; i < tab_count; i++) {
            ((LinearLayout) tabs.getTabAt(i).view).setVisibility(View.GONE);
        }


        //해당하는 역의 호선 탭 활성화
        for (int i = 0; i < count; i++) {
            Toast.makeText(getApplicationContext(), "test" + Staionline[i], Toast.LENGTH_LONG).show();
            int line = Integer.parseInt(Staionline[i]);
            ((LinearLayout) tabs.getTabAt(line - 1).view).setVisibility(View.VISIBLE);
        }

        //프라그먼트 첫번째 화면 지정
        int line = 1;
        switch (line) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view).commit();
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view).commit();
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view).commit();
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view).commit();
        }


        //메뉴 클릭시

        //클릭 시 fragment 변경
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if (position == 0) {
                    selected = detailed_view;
                } else if (position == 1) {
                    selected = detailed_view;
                } else if (position == 2) {
                    selected = detailed_view;
                } else if (position == 3) {
                    selected = detailed_view;
                } else if (position == 4) {
                    selected = detailed_view;
                } else if (position == 5) {
                    selected = detailed_view;
                } else if (position == 6) {
                    selected = detailed_view;
                } else if (position == 7) {
                    selected = detailed_view;
                } else if (position == 8) {
                    selected = detailed_view;
                } else if (position == 9) {
                    selected = detailed_view;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    //메뉴 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_refresh:
                Toast.makeText(this, "설정메뉴", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}



        //카카오 map
//        MapView mapView = new MapView(this);
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.Kakao_map);
//        mapViewContainer.addView(mapView);

        //툴바 세팅

