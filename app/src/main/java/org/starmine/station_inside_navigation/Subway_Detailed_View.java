package org.starmine.station_inside_navigation;

<<<<<<< HEAD
=======
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
>>>>>>> origin/main
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.daum.mf.map.api.MapView;


public class Subway_Detailed_View extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_detailed_view);

        //카카오 map
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.Kakao_map);
        mapViewContainer.addView(mapView);

        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Detail_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //경로찾기 버튼
        Button Detail_Route_Btn = (Button) findViewById(R.id.Detail_Route_Btn);
        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Route.class);
                startActivity(intent);
            }
        });

        /* 역 내부안내 만들어지면 수정할 인텐트
        Button Detail_Route_Btn = (Button) findViewById(R.id.Detail_Route_Btn);
        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Route.class);
                startActivity(intent);
            }
        });*/


        // 시간표 버튼
        Button Deatil_Schedule_Btn = (Button) findViewById(R.id.Deatil_Schedule_Btn);
        Deatil_Schedule_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Schedule.class);
                startActivity(intent);
            }
        });

        Button Detail_Bookmark_Btn = (Button) findViewById(R.id.Detail_Bookmark_Btn);
        Detail_Bookmark_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bookmark.class);
                startActivity(intent);
            }
        });

        //문의하기 버튼
        Button Detail_Inquire_Btn = (Button) findViewById(R.id.Detail_Inquire_Btn);
        Detail_Inquire_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Inquiry_Page.class);
                startActivity(intent);
            }
        });


    }



    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //메뉴 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
<<<<<<< HEAD


=======
>>>>>>> origin/main
}
