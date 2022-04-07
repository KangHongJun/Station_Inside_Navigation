package org.starmine.station_inside_navigation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class Subway_Map extends AppCompatActivity {

    SubsamplingScaleImageView imageView;
    private GestureDetector mDetector;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_map);

        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Subway_Map_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //퀵 액션 예제
       



        imageView = findViewById(R.id.SubwayMap_Img);
        imageView.setImage(ImageSource.resource(R.drawable.subway_map));


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return false;
            }
        });

        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {


            @Override
            public boolean onDown(MotionEvent event) {


                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                DBHelper Helper;
                SQLiteDatabase sqlDB;
                Helper = new DBHelper(Subway_Map.this,"subway_info.db",null,1);
                sqlDB = Helper.getReadableDatabase();
                Helper.onCreate(sqlDB);
                Cursor cursor;
                cursor = sqlDB.rawQuery("select * from subway_coordinate",null);

                PointF sCoord = imageView.viewToSourceCoord(event.getX(), event.getY());
                int x_cor = (int) sCoord.x;
                int y_cor = (int) sCoord.y;

                if (cursor.moveToFirst()){
                    do{
                        if ((x_cor > cursor.getInt(2)) && (x_cor < cursor.getInt(4)) && (y_cor > cursor.getInt(3)) && (y_cor < cursor.getInt(5))) {
                            String targetStation = cursor.getString(1);

                            Toast.makeText(getApplicationContext(), targetStation, Toast.LENGTH_LONG).show();


                        } // send Station Name (column 1)
                    } while (cursor.moveToNext());

                }

                Toast.makeText(getApplicationContext(),"x: "+x_cor+ "y :"+y_cor,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });



    }




    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
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
}