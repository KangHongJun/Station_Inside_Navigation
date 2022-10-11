package fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.starmine.station_inside_navigation.R;

public class Beomgyeb2 extends Fragment {
    ViewGroup viewGroup;
    ImageView BeomgyeB2,currentS;

    Canvas canvas;



    int stationnum;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.beomgye_b2, container, false);

        BeomgyeB2 = viewGroup.findViewById(R.id.BeomgyeB2_Img);

        //bitmap이미지 생성하여 캔버스로 사용
        Resources resources = getResources();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b2);
        Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888,true);
        canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,new Matrix(),null);
        BeomgyeB2.setImageBitmap(bitmap);




        currentS = viewGroup.findViewById(R.id.currentS);
        currentS.setVisibility(View.INVISIBLE);


        Bundle curstation = getArguments();
        if(curstation != null){
            stationnum = curstation.getInt("stationnum");
        }

        if (stationnum==1){
            setstairColor(bitmap);
        }





        return viewGroup;
    }

    public void setstairColor(Bitmap bitmap){
        //이미지 위에 선 그리기
        Bitmap overlay = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);

        //좌표 기준
        canvas.drawLine(985,440,1169,440,paint);
        canvas.drawBitmap(overlay,100,100,paint);

        BeomgyeB2.setImageBitmap(bitmap);
        System.out.println(canvas.getWidth()+"/"+canvas.getHeight());


        currentS.setVisibility(View.VISIBLE);

    }
}
