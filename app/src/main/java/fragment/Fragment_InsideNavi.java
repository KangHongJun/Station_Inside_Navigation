package fragment;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.starmine.station_inside_navigation.DBHelper;
import org.starmine.station_inside_navigation.DatabaseHelper;
import org.starmine.station_inside_navigation.Graph;
import org.starmine.station_inside_navigation.R;

public class Fragment_InsideNavi extends Fragment {
    ViewGroup viewGroup;
    ImageView insideMap_Img;
    Canvas canvas;
    int stationnum;
    String curdata;
    DatabaseHelper db;

    BitmapDrawable bitmapDrawable;//지도 이미지 비트맵 변환,저장

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.inside_map, container, false);

        insideMap_Img = viewGroup.findViewById(R.id.insideMap_Img_real);

        Bundle curstaion_floor = getArguments();
        if(curstaion_floor != null){
            curdata = curstaion_floor.getString("floor");
        }

        Resources resources = getResources();
        //db에 비트맵이미지 저장하는 방식으로 수정가능 - curdata로 받거나....

        if (curdata.equals("범계B1")){
            bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b1);
        }else if (curdata.equals("범계B2")){
            bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b2);
        }

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

    public void setstairColor(Bitmap bitmap){  //매개변수 추가
        System.out.println("we");
        //이미지 위에 선 그리기
        //위에 그림
        db = new DatabaseHelper(getActivity());
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);
        Cursor cursor_test = sqlDB.rawQuery("select * from BeomB2_test",null);


        Graph navigation;
        navigation = new Graph(14);
        while (cursor_test.moveToNext()){
            navigation.input(cursor_test.getInt(0),cursor_test.getInt(1),cursor_test.getInt(2));
        }

        int route_time = navigation.dijkstra(10,12);
        int[] a = navigation.getRoute();
        //System.out.println(route_time+"프라그먼트"+a[0]);


        Bitmap overlay = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);

        //a배열에 시작점 끝점을 양 끝에 추가하고, 그것과 A B위치가 둘다 같은걸 찾아서 진행
        cursor_test.moveToFirst();
        for(int i=0;i<a.length-1;i++){
            System.out.println(a[i]+"노드번호");
            while (cursor_test.moveToNext()){
                int A = cursor_test.getInt(0);
                int B = cursor_test.getInt(1);
            }
        }

        //시작지점에 원 추가 panint2 생성해서 다른 형태지정
        canvas.drawLine(765,190,765,230,paint);
        canvas.drawLine(765,230,1080,230,paint);
        canvas.drawBitmap(overlay,100,100,paint);

        insideMap_Img.setImageBitmap(bitmap);
        System.out.println(canvas.getWidth()+"/"+canvas.getHeight());

    }
}
