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
//새로 만들어서 받은 값에따라 drawable세팅하기 - 이미지 설정은 db에 이름을 넣어서 하거나..
public class Beomgyeb1 extends Fragment {
    ViewGroup viewGroup;
    ImageView insideMap_Img,insideMap_Img2;
    Canvas canvas;

    int stationnum;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.beomgye_b1, container, false);


        insideMap_Img = viewGroup.findViewById(R.id.insideMap_Img);
        insideMap_Img2 = viewGroup.findViewById(R.id.insideMap_Img2);


        Resources resources = getResources();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b1);

        Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888,true);
        insideMap_Img.setImageBitmap(bitmap);

        //지하철
        canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,new Matrix(),null);

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
        //위에 그림
        Bitmap overlay = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);

        canvas.drawLine(765,190,765,230,paint);
        canvas.drawLine(765,230,1080,230,paint);
        canvas.drawBitmap(overlay,100,100,paint);

        insideMap_Img.setImageBitmap(bitmap);
        System.out.println(canvas.getWidth()+"/"+canvas.getHeight());

    }
}
