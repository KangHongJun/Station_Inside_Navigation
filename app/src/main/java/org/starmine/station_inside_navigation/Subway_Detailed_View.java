package org.starmine.station_inside_navigation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import fragment.Fragment_Detail;
import fragment.Fragment_Detail_line1;
import fragment.Fragment_Detail_line2;
import fragment.Fragment_Detail_line3;
import fragment.Fragment_Detail_line4;
import fragment.Fragment_Detail_line5;
import fragment.Fragment_Detail_line6;
import fragment.Fragment_Detail_line7;
import fragment.Fragment_Detail_line8;
import fragment.Fragment_Detail_line9;


public class Subway_Detailed_View extends AppCompatActivity{

    Fragment_Detail detailed_view;
    Fragment_Detail_line1 detailed_view1;
    Fragment_Detail_line2 detailed_view2;
    Fragment_Detail_line3 detailed_view3;
    Fragment_Detail_line4 detailed_view4;
    Fragment_Detail_line5 detailed_view5;
    Fragment_Detail_line6 detailed_view6;
    Fragment_Detail_line7 detailed_view7;
    Fragment_Detail_line8 detailed_view8;
    Fragment_Detail_line9 detailed_view9;


    private static Cursor cursor_line;
    private static String curStation;
    //public변경하기
    public TabLayout tabs;

    //선택된 탭
    static int tab_position;

    //해당 역 호선 수
    static int count;
    //호선 정보
    static String[] Staionline = new String[10];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_detailed_view);

        Toolbar toolbar = findViewById(R.id.Detail_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

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
        count = cursor_line.getCount();

        //역 호선 정보

        for (int i = 0; i < count; i++) {
            cursor_line.moveToNext();
            Staionline[i] = cursor_line.getString(3);
            //Toast.makeText(getApplicationContext(), ""+Staionline[i], Toast.LENGTH_LONG).show();
        }

        detailed_view = new Fragment_Detail();
        detailed_view1 = new Fragment_Detail_line1();
        detailed_view2 = new Fragment_Detail_line2();
        detailed_view3 = new Fragment_Detail_line3();
        detailed_view4 = new Fragment_Detail_line4();
        detailed_view5 = new Fragment_Detail_line5();
        detailed_view6 = new Fragment_Detail_line6();
        detailed_view7 = new Fragment_Detail_line7();
        detailed_view8 = new Fragment_Detail_line8();
        detailed_view9 = new Fragment_Detail_line9();


//프라크먼트 데이터 이동
        Bundle bundle = new Bundle();
        bundle.putString("station", curStation);
        detailed_view.setArguments(bundle);

//        detailed_view1.setArguments(bundle);
//        detailed_view2.setArguments(bundle);
//        detailed_view3.setArguments(bundle);
        //detailed_view4.setArguments(bundle);
//        detailed_view5.setArguments(bundle);
//        detailed_view6.setArguments(bundle);
//        detailed_view7.setArguments(bundle);


        for (int i = 0; i < count; i++) {
            int line = Integer.parseInt(Staionline[i]);
            if (line==1){
                bundle.putString("station", curStation);
                detailed_view1.setArguments(bundle);
            }else if(line==2){
                bundle.putString("station", curStation);
                detailed_view2.setArguments(bundle);
            }
            else if(line==3){
                bundle.putString("station", curStation);
                detailed_view3.setArguments(bundle);
            }
            else if(line==4){
                bundle.putString("station", curStation);
                detailed_view4.setArguments(bundle);
            }
            else if(line==5){
                bundle.putString("station", curStation);
                detailed_view5.setArguments(bundle);
            }
            else if(line==6){
                bundle.putString("station", curStation);
                detailed_view6.setArguments(bundle);
            }
            else if(line==7){
                bundle.putString("station", curStation);
                detailed_view7.setArguments(bundle);
            }
            else if(line==8){
                bundle.putString("station", curStation);
                detailed_view8.setArguments(bundle);
            }
            else if(line==9){
                bundle.putString("station", curStation);
                detailed_view9.setArguments(bundle);
            }

        }

        


//        //탭 숨기기 gone
//        int tab_count = tabs.getTabCount();
//        for (int i = 0; i < tab_count; i++) {
//            ((LinearLayout) tabs.getTabAt(i).view).setVisibility(View.GONE);
//        }
//
//        //해당하는 역의 호선 탭 활성화
//        for (int i = 0; i < count; i++) {
//            //Toast.makeText(getApplicationContext(), "test" + Staionline[i], Toast.LENGTH_LONG).show();
//            int line = Integer.parseInt(Staionline[i]);
//            ((LinearLayout) tabs.getTabAt(line - 1).view).setVisibility(View.VISIBLE);
//        }
        Setting_tab();



        //프라그먼트 첫번째 화면 지정
        int line = Integer.parseInt(Staionline[0]);
        //선택 탭 번호 tab_position에 저장
        tab_position = Integer.parseInt(Staionline[0]);

        //Toast.makeText(getApplicationContext(),""+line,Toast.LENGTH_SHORT).show();
        switch (line) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view1).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view2).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view3).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view4).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view5).commit();
                break;
            case 6:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view6).commit();
                break;
            case 7:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view7).commit();
                break;
            case 8:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view8).commit();
                break;
            case 9:
                getSupportFragmentManager().beginTransaction().replace(R.id.Subway_Detail_Container, detailed_view9).commit();
                break;
            default:
                break;
        }



        //클릭 시 fragment 변경
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if (position == 0) {
                    selected = detailed_view1;
                    tab_position = 1;
                } else if (position == 1) {
                    selected = detailed_view2;
                    tab_position = 2;
                } else if (position == 2) {
                    selected = detailed_view3;
                    tab_position = 3;
                } else if (position == 3) {
                    selected = detailed_view4;
                    tab_position = 4;
                } else if (position == 4) {
                    selected = detailed_view5;
                    tab_position = 5;
                } else if (position == 5) {
                    selected = detailed_view6;
                    tab_position = 6;
                } else if (position == 6) {
                    selected = detailed_view7;
                    tab_position = 7;
                } else if (position == 7) {
                    selected = detailed_view8;
                    tab_position = 8;
                } else if (position == 8) {
                    selected = detailed_view9;
                    tab_position = 9;
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

    //탭 세팅
    public void Setting_tab(){
        tabs = findViewById(R.id.Detail_subway_line_Tab);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line1));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line2));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line3));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line4));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line5));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line6));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line7));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line8));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.img_line9));

        int tab_count = tabs.getTabCount();
        for (int i = 0; i < tab_count; i++) {
            ((LinearLayout) tabs.getTabAt(i).view).setVisibility(View.GONE);
        }
        //tabs.selectTab(tabs.getTabAt(4));

        //해당하는 역의 호선 탭 활성화
        for (int i = 0; i < count; i++) {
            //Toast.makeText(getApplicationContext(), "test" + Staionline[i], Toast.LENGTH_LONG).show();
            int line = Integer.parseInt(Staionline[i]);
            ((LinearLayout) tabs.getTabAt(line - 1).view).setVisibility(View.VISIBLE);
        }
    }

    public void Update_tab(String station){
        curStation = station;

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Detailed_View.this, "subway_info.db", null, 1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

//        Intent get_intent = getIntent();
//        curStation = get_intent.getStringExtra("station");

        String sql_line = "select * from subway_line where NAME = " + "\"" + station + "\"" + "";
        cursor_line = sqlDB.rawQuery(sql_line, null);
        count = cursor_line.getCount();

        //역 호선 정보
        for (int i = 0; i < count; i++) {
            cursor_line.moveToNext();
            Staionline[i] = cursor_line.getString(3);
            //Toast.makeText(getApplicationContext(), ""+Staionline[i], Toast.LENGTH_LONG).show();
        }

//        tabs = findViewById(R.id.Detail_subway_line_Tab);
//        tabs.addTab(tabs.newTab().setText("1"));
//        tabs.addTab(tabs.newTab().setText("2"));
//        tabs.addTab(tabs.newTab().setText("3"));
//        tabs.addTab(tabs.newTab().setText("4"));
//        tabs.addTab(tabs.newTab().setText("5"));
//        tabs.addTab(tabs.newTab().setText("6"));
//        tabs.addTab(tabs.newTab().setText("7"));
//        tabs.addTab(tabs.newTab().setText("8"));

        int tab_count = tabs.getTabCount();
        for (int i = 0; i < tab_count; i++) {
            ((LinearLayout) tabs.getTabAt(i).view).setVisibility(View.GONE);
        }

        //해당하는 역의 호선 탭 활성화
        for (int i = 0; i < count; i++) {
            //Toast.makeText(getApplicationContext(), "test" + Staionline[i], Toast.LENGTH_LONG).show();
            int line = Integer.parseInt(Staionline[i]);
            ((LinearLayout) tabs.getTabAt(line - 1).view).setVisibility(View.VISIBLE);
        }

        Bundle bundle = new Bundle();
        bundle.putString("station", station);
        //역이름 출력
        //Toast.makeText(getApplicationContext(),station,Toast.LENGTH_SHORT).show();

        for (int i = 0; i < count; i++) {
            int line = Integer.parseInt(Staionline[i]);
            if (line==1){
                bundle.putString("station", station);
                detailed_view1.setArguments(bundle);
            }else if(line==2){
                bundle.putString("station", station);
                detailed_view2.setArguments(bundle);
            }
            else if(line==3){
                bundle.putString("station", station);
                detailed_view3.setArguments(bundle);
            }
            else if(line==4){
                bundle.putString("station", station);
                detailed_view4.setArguments(bundle);
            }
            else if(line==5){
                bundle.putString("station", station);
                detailed_view5.setArguments(bundle);
            }
            else if(line==6){
                bundle.putString("station", station);
                detailed_view6.setArguments(bundle);
            }else if(line==7) {
                bundle.putString("station", station);
                detailed_view7.setArguments(bundle);
            }else if(line==8) {
                bundle.putString("station", station);
                detailed_view8.setArguments(bundle);
            }else if(line==9) {
                bundle.putString("station", station);
                detailed_view9.setArguments(bundle);
            }
        }
    }



    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu,menu);

        return true;
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

                //tab_position 값에 따라 새로고침 프라그먼트 설정
                switch (tab_position){
                    case 1:
                        Fragment_Detail_line1 line1 = (Fragment_Detail_line1) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
               line1.setArrivalTime();
               break;
                    case 2:
                        Fragment_Detail_line2 line2 = (Fragment_Detail_line2) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line2.setArrivalTime();
                        break;
                    case 3:
                        Fragment_Detail_line3 line3 = (Fragment_Detail_line3) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line3.setArrivalTime();
                        break;
                    case 4:
                        Fragment_Detail_line4 line4 = (Fragment_Detail_line4) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line4.setArrivalTime();
                        break;
                    case 5:
                        Fragment_Detail_line5 line5 = (Fragment_Detail_line5) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line5.setArrivalTime();
                        break;
                    case 6:
                        Fragment_Detail_line6 line6 = (Fragment_Detail_line6) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line6.setArrivalTime();
                        break;
                    case 7:
                        Fragment_Detail_line7 line7 = (Fragment_Detail_line7) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line7.setArrivalTime();
                        break;
                    case 8:
                        Fragment_Detail_line8 line8 = (Fragment_Detail_line8) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line8.setArrivalTime();
                        break;
                    case 9:
                        Fragment_Detail_line9 line9 = (Fragment_Detail_line9) getSupportFragmentManager().findFragmentById(R.id.Subway_Detail_Container);
                        line9.setArrivalTime();
                        break;
                    default:
                        break;

                }
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

