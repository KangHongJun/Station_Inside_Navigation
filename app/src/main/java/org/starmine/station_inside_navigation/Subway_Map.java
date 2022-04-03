package org.starmine.station_inside_navigation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;


public class Subway_Map extends AppCompatActivity {

    //이미지 줌인 최대치 조절 변수
    private ScaleGestureDetector scaleGestureDetector;
    private float ScaleFactor = 1.0f;
    SubsamplingScaleImageView imageView;


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

        //이미지 줌인 함수 적용
        imageView = findViewById(R.id.SubwayMap_Img);
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());

        Drawable bitmap = getResources().getDrawable(R.drawable.subway_map);
    }
    //메뉴 적용

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }


    public boolean onTouchEvent(MotionEvent motionEvent){
        scaleGestureDetector.onTouchEvent(motionEvent);

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            ScaleFactor *= scaleGestureDetector.getScaleFactor();

            ScaleFactor = Math.max(0.01f,
                    Math.min(ScaleFactor,10.f));


            imageView.setScaleX(ScaleFactor);
            imageView.setScaleY(ScaleFactor);

            return true;
        }
    }
}

