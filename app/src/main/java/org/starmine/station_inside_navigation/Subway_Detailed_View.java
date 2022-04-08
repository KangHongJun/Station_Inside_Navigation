package org.starmine.station_inside_navigation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;


public class Subway_Detailed_View extends AppCompatActivity {
    Fragment_Weekday Weekday;
    Fragment_Saturday Saturday;
    Fragment_Holiday Holiday;

    private static Cursor cursor_line;
    private static String curStation;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_detailed_view);

        //DB class따로 만들기
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Detailed_View.this,"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);


        //intent로 받은 지하철이름에 해당하는  값 조회
        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");


        String sql_line = "select * from subway_line where NAME = " +"\""+ curStation +"\""+"";
        cursor_line = sqlDB.rawQuery(sql_line,null);
        int count = cursor_line.getCount();

        String[] Staionline = new String[10];//역 호선 정보

        for(int i=0;i<count;i++){
            cursor_line.moveToNext();
            Staionline[i] = cursor_line.getString(3);
            //Toast.makeText(getApplicationContext(), ""+Staionline[i], Toast.LENGTH_LONG).show();
        }


        Weekday = new Fragment_Weekday();
        Saturday = new Fragment_Saturday();
        Holiday = new Fragment_Holiday();

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
        for (int i=0;i<tab_count;i++){
            ((LinearLayout) tabs.getTabAt(i).view).setVisibility(View.GONE);
        }



        //해당하는 역의 호선 탭 활성화
        for (int i=0;i<count;i++){
            Toast.makeText(getApplicationContext(),"test"+Staionline[i],Toast.LENGTH_LONG).show();
            int line = Integer.parseInt(Staionline[i]);
            ((LinearLayout) tabs.getTabAt(line-1).view).setVisibility(View.VISIBLE);
        }

        int line = 1;
        switch (line){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container,Weekday).commit();
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container,Holiday).commit();
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container,Saturday).commit();
            default:
                Toast.makeText(getApplicationContext(), "ww", Toast.LENGTH_SHORT).show();

        }





        //클릭 시 fragment 변경
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if (position == 0){
                    selected = Weekday;
                }else if (position == 1){
                    selected = Saturday;
                }else if (position == 2){
                    selected = Holiday;
                }else if (position == 3){
                    selected = Holiday;
                }else if (position == 4){
                    selected = Holiday;
                }else if (position == 5){
                    selected = Holiday;
                }else if (position == 6){
                    selected = Holiday;
                }else if (position == 7){
                    selected = Holiday;
                }else if (position == 8){
                    selected = Holiday;
                } else if (position == 9){
                    selected = Holiday;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container,selected).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }










        //카카오 map
//        MapView mapView = new MapView(this);
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.Kakao_map);
//        mapViewContainer.addView(mapView);

        //툴바 세팅


//        //경로찾기 버튼
//        Button Detail_Route_Btn = (Button) findViewById(R.id.Detail_Route_Btn);
//        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Subway_Route.class);
//                startActivity(intent);
//            }
//        });
//
//        /* 역 내부안내 만들어지면 수정할 인텐트
//        Button Detail_Route_Btn = (Button) findViewById(R.id.Detail_Route_Btn);
//        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Subway_Route.class);
//                startActivity(intent);
//            }
//        });*/
//
//
//        // 시간표 버튼
//        Button Deatil_Schedule_Btn = (Button) findViewById(R.id.Deatil_Schedule_Btn);
//        Deatil_Schedule_Btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Subway_Schedule.class);
//                startActivity(intent);
//            }
//        });
//
//        Button Detail_Bookmark_Btn = (Button) findViewById(R.id.Detail_Bookmark_Btn);
//        Detail_Bookmark_Btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Bookmark.class);
//                startActivity(intent);
//            }
//        });
//
//        //문의하기 버튼
//        Button Detail_Inquire_Btn = (Button) findViewById(R.id.Detail_Inquire_Btn);
//        Detail_Inquire_Btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Inquiry_Page.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//
//
//
//    //메뉴 적용
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.detail_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    //메뉴 클릭시
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int curId = item.getItemId();
//        switch (curId) {
//            case R.id.menu_refresh:
//                Toast.makeText(this, "설정메뉴", Toast.LENGTH_LONG).show();
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);

}
