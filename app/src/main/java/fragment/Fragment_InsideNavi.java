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

import java.util.ArrayList;

public class Fragment_InsideNavi extends Fragment {
    ViewGroup viewGroup;
    ImageView insideMap_Img;
    Canvas canvas;
    int stationnum;
    String curdata;
    DatabaseHelper db;

    int StartL, EndL, FloorCount;
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

    public void setstairColor(Bitmap bitmap){
        //이미지 위에 선 그리기
        //위에 그림
        db = new DatabaseHelper(getActivity());
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        Cursor cursor_all = sqlDB.rawQuery("select * from BeomB2_test2;",null);
        Cursor cursor_get_node = sqlDB.rawQuery("select max(B/100) from BeomB2_test2;",null);

        cursor_get_node.moveToFirst();
        FloorCount = cursor_get_node.getInt(0);
        int NodeCount = cursor_all.getCount();


        //경로찾기 알고리즘
        Graph navigation;
        navigation = new Graph(NodeCount);
        ArrayList<Integer> floorNodeCount = new ArrayList<>();
        floorNodeCount.add(0);

        for(int i=1;i<FloorCount+1;i++){
            String SQl = "select * from BeomB2_test2 where A/100=" +i;

            Cursor cursor_test = sqlDB.rawQuery(SQl,null);

            String SQl_count = "select max(B) from BeomB2_test2 where B/100=" +i;
            Cursor nodeCount = sqlDB.rawQuery(SQl_count,null);

            nodeCount.moveToFirst();
            floorNodeCount.add(nodeCount.getInt(0));

            int testfllor = nodeCount.getInt(0);
            System.out.println(testfllor);
            cursor_test.moveToFirst();
            while (true){
                try{
                    int Mnode = 100*i-floorNodeCount.get(i - 1);
                    navigation.input(cursor_test.getInt(0)-Mnode,cursor_test.getInt(1)-Mnode,cursor_test.getInt(2));
                    cursor_test.moveToNext();
                }catch (Exception e){
                    break;
                }


            }
        }







        //루트 반환 및 지도 그리기

        //시작 및 도착지점도 번들 데이터로 받아야한다
        StartL = 5;
        EndL=13;
        //큰값보다는 작은값에서 시작하는 것이 잘되기 값 조정
//        if (StartL>EndL){
//            navigation.dijkstra(StartL,EndL);
//        }else if(StartL<EndL){
//            navigation.dijkstra(EndL,StartL);
//        }

        navigation.dijkstra(0,6);

        ArrayList route_list = navigation.getRoute_list();

        for(int i=0;i<route_list.size();i++){
            System.out.println(route_list.get(i));
        }

        Bitmap overlay = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);

        //위에서 각 구간별로 input은 했으니 나머지 고민해보기
//        for(int i=0;i<route_list.size();i++){
//           //System.out.println(route_list.get(i) +"포문 노드");
//            cursor_test.moveToFirst();
//            while (true){
//                try{
//                    int A = cursor_test.getInt(0);
//                    int B = cursor_test.getInt(1);
//                    if (route_list.get(i).equals(A) && route_list.get(i + 1).equals(B)){
//                        canvas.drawLine(cursor_test.getInt(3),cursor_test.getInt(4),
//                                cursor_test.getInt(5),cursor_test.getInt(6),paint);
//                    }else if(route_list.get(i).equals(B) && route_list.get(i + 1).equals(A)){
//                        canvas.drawLine(cursor_test.getInt(3),cursor_test.getInt(4),
//                                cursor_test.getInt(5),cursor_test.getInt(6),paint);
//                    }
//                    cursor_test.moveToNext();
//                }catch (Exception e){
//                    break;
//                }
//            }
//        }

        //시작지점에 원 추가 panint2 생성해서 다른 형태지정 하기위해서는 따로 값 지정 해놔야함
        //화살표는 나중에

        canvas.drawBitmap(overlay,100,100,paint);

        insideMap_Img.setImageBitmap(bitmap);
        System.out.println(canvas.getWidth()+"/"+canvas.getHeight());

    }
}
