package org.starmine.station_inside_navigation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class testfile extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private float ScaleFactor = 1.0f;
    SubsamplingScaleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmap);

        Toolbar toolbar = findViewById(R.id.Subway_Map_Toolbar);
        setSupportActionBar(toolbar);


        //이미지 줌인 함수 적용
        imageView = findViewById(R.id.SubwayMap_Img);
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());

        Drawable bitmap = getResources().getDrawable(R.drawable.subway_map);

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