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

public class Beomgyeb1 extends Fragment {
    ViewGroup viewGroup;
    ImageView insideMap_Img,insideMap_Img2;

    int stationnum;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.beomgye_b1, container, false);


        insideMap_Img = viewGroup.findViewById(R.id.insideMap_Img);
        insideMap_Img2 = viewGroup.findViewById(R.id.insideMap_Img2);


        Resources resources = getResources();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b1);

        Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888,true);

        //지하철
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,new Matrix(),null);

        //위에 그림
        Bitmap overlay = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(4f);

        canvas.drawLine(0,0,100,100,paint);
        canvas.drawBitmap(overlay,100,100,paint);

        insideMap_Img.setImageBitmap(bitmap);
        System.out.println(insideMap_Img.getWidth()+"/"+insideMap_Img.getHeight());


        return viewGroup;
    }
}
