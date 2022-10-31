package fragment;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.starmine.station_inside_navigation.DBHelper;
import org.starmine.station_inside_navigation.DatabaseHelper;
import org.starmine.station_inside_navigation.Graph;
import org.starmine.station_inside_navigation.Inside_Navigation_test;
import org.starmine.station_inside_navigation.R;

import java.sql.Blob;
import java.util.ArrayList;

public class Fragment_InsideNavi_Song extends Fragment {
    ViewGroup viewGroup;
    ImageView insideMap_Img;
    Canvas canvas;
    int stationnum;
    String curdata;
    String curstation;
    String curfloor;
    DatabaseHelper db;
    int MNodeCount;

    int StartL, EndL, FloorCount,Mnode;
    BitmapDrawable bitmapDrawable;//지도 이미지 비트맵 변환,저장

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        viewGroup = (ViewGroup) inflater.inflate(R.layout.inside_map, container, false);

        insideMap_Img = viewGroup.findViewById(R.id.insideMap_Img_real);

        Bundle cur_info = getArguments();
        curstation = cur_info.getString("Name");
        curfloor = cur_info.getString("Floor");
        System.out.println("#############"+curstation);

        if(cur_info != null){
            //curdata = cur_info.getString("floor");
            curstation = cur_info.getString("Name");
            curfloor = cur_info.getString("Floor");
        }

        Toast.makeText(getActivity(), curstation.concat(curfloor), Toast.LENGTH_LONG).show();

        Resources resources = getResources();
        //db에 비트맵이미지 저장하는 방식으로 수정가능 - curdata로 받거나....

        String sql = "Select Floor_Image from Floor_TB Where Station_Name = '" + curstation + "' AND Floor_Nm = '" + curfloor + "'";
        Cursor Floor_cursor = sqlDB.rawQuery(sql,null);

        Floor_cursor.moveToLast();

        byte[] Image = Floor_cursor.getBlob(0);

        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length).copy(Bitmap.Config.ARGB_8888, true);

        insideMap_Img.setImageBitmap(bitmap);

        String str = curfloor.substring(1);
        int floorcnt = Integer.parseInt(str);

        canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,new Matrix(),null);

        Bundle curstation = getArguments();
        if(curstation != null){
            stationnum = curstation.getInt("stationnum");
        }
        System.out.println(stationnum+"B1");
        setstairColor(bitmap,floorcnt);

        /*if (stationnum==1){
            setstairColor(bitmap,floorcnt);
        }*/

        /*if (curfloor.equals("B1")){
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap,new Matrix(),null);

            Bundle curstation = getArguments();
            if(curstation != null){
                stationnum = curstation.getInt("stationnum");
            }
            System.out.println(stationnum+"B1");
            if (stationnum==1){
                setstairColor(bitmap,floorcnt);
            }
        }else if (curfloor.equals("B2")){
            //지하철
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap,new Matrix(),null);

            Bundle curstation = getArguments();
            if(curstation != null){
                stationnum = curstation.getInt("stationnum");
            }
            System.out.println(stationnum+"B2");
            if (stationnum==1){
                setstairColor(bitmap,2);
            }
        }*/



        return viewGroup;
    }


        /*if (curdata.equals("범계B1")){
            bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b1);
            Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888,true);
            insideMap_Img.setImageBitmap(bitmap);

            //지하철
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap,new Matrix(),null);

            Bundle curstation = getArguments();
            if(curstation != null){
                stationnum = curstation.getInt("stationnum");
            }
            System.out.println(stationnum+"B1");
            if (stationnum==1){
                setstairColor(bitmap,1);
            }
        }else if (curdata.equals("범계B2")){
            bitmapDrawable = (BitmapDrawable)resources.getDrawable(R.drawable.beomgye_b2);
            Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888,true);
            insideMap_Img.setImageBitmap(bitmap);

            //지하철
            canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmap,new Matrix(),null);

            Bundle curstation = getArguments();
            if(curstation != null){
                stationnum = curstation.getInt("stationnum");
            }
            System.out.println(stationnum+"B2");
            if (stationnum==1){
                setstairColor(bitmap,2);
            }
        }



        return viewGroup;
    }*/

    public void setstairColor(Bitmap bitmap,int Curfloor){
        System.out.println("----------------------"+Curfloor);
        //이미지 위에 선 그리기
        //위에 그림
        db = new DatabaseHelper(getActivity());
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        Cursor cursor_all = sqlDB.rawQuery("select * from BeomB2_test3;",null);
        int NodeCount = cursor_all.getCount();

        Cursor cursor_get_node = sqlDB.rawQuery("select max(B/100) from BeomB2_test3;",null);

        cursor_get_node.moveToFirst();
        FloorCount = cursor_get_node.getInt(0);



        //경로찾기 알고리즘
        Graph navigation;
        navigation = new Graph(NodeCount+1);
        ArrayList<Integer> floorNodeCount = new ArrayList<>();
        floorNodeCount.add(0);

        for(int i=1;i<FloorCount+1;i++){
            String SQl = "select * from BeomB2_test3 where A/100=" +i;
            Cursor cursor_test = sqlDB.rawQuery(SQl,null);

            String SQl_count = "select max(max(B),max(A)) from BeomB2_test3 where B/100=" +i;
            Cursor nodeCount = sqlDB.rawQuery(SQl_count,null);


            nodeCount.moveToFirst();

            //MNodeCount = nodeCount.getInt(0);//나중엔 이걸 쓸지도?

            floorNodeCount.add(floorNodeCount.get(i-1) + nodeCount.getInt(0)-100*i+1);
            System.out.println(floorNodeCount+"floorNodeCount");
            Mnode = 100*(i)-floorNodeCount.get(i-1);
            System.out.println(Mnode+"Mnode");


            cursor_test.moveToFirst();
            for (int j=0;j<cursor_test.getCount();j++){
                if (cursor_test.getInt(1)>=(i+1)*100){
                    int Mnode2 = 100*(i+1)-floorNodeCount.get(i);
                    navigation.input(cursor_test.getInt(0)-Mnode,cursor_test.getInt(1)-Mnode2,cursor_test.getInt(2));
                    System.out.println(cursor_test.getInt(0)-Mnode+"if일때 A");
                    System.out.println(cursor_test.getInt(1)-Mnode2+"if일때 B");
                }else {
                    navigation.input(cursor_test.getInt(0)-Mnode,cursor_test.getInt(1)-Mnode,cursor_test.getInt(2));
                    System.out.println(cursor_test.getInt(0)-Mnode+" A");
                    System.out.println(cursor_test.getInt(1)-Mnode+" B");
                }
                cursor_test.moveToNext();
            }
        }







        //루트 반환 및 지도 그리기

        //시작 및 도착지점도 번들 데이터로 받아야한다


        Bundle StartI = getArguments();
        if(StartI != null){
            if (StartI.getInt("start")>0){
                StartL = StartI.getInt("start")-1;
            }
        }
        Bundle EndI = getArguments();
        if(EndI != null){
            if (EndI.getInt("end")>0){
                EndL =  EndI.getInt("end")+floorNodeCount.get(1)-1;

            }

        }
//        StartL = 5;
//        EndL=13;
        //큰값보다는 작은값에서 시작하는 것이 잘되기 값 조정
//        if (StartL>EndL){
//            navigation.dijkstra(StartL,EndL);
//        }else if(StartL<EndL){
//            navigation.dijkstra(EndL,StartL);
//        }
        System.out.println(StartL+"starttt");
        System.out.println(EndL+"endlll");


        navigation.dijkstra(StartL,EndL);

        ArrayList route_list = navigation.getRoute_list();

        //노드값 조정
        ArrayList<Integer> result_list = new ArrayList<>();
        for(int i=0;i<route_list.size();i++){
            System.out.println(route_list.get(i));
            int route_list_value = (int) route_list.get(i);
            if (route_list_value>floorNodeCount.get(FloorCount-Curfloor) && route_list_value<floorNodeCount.get(FloorCount-Curfloor+1)){
                    int result = (int)route_list.get(i)-floorNodeCount.get(FloorCount-Curfloor)+100*(FloorCount-Curfloor+1);
                    result_list.add(result);



//            for(int j=0;j<floorNodeCount.size()-1;j++){
//                if (route_list_value>floorNodeCount.get(j) && route_list_value<floorNodeCount.get(j+1)){
//                    int result = (int)route_list.get(i)-floorNodeCount.get(j)+100*(j+1);
//                    result_list.add(result);
//                }
//
            }
        }

        System.out.println(result_list);

        Bitmap overlay = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);

        //그림그리기
        for(int i=0;i<result_list.size();i++){
            cursor_all.moveToFirst();
            while (true){
                try{
                    int A = cursor_all.getInt(0);
                    int B = cursor_all.getInt(1);
                    if (result_list.get(i).equals(A) && result_list.get(i + 1).equals(B)){
                        canvas.drawLine(cursor_all.getInt(3),cursor_all.getInt(4),
                                cursor_all.getInt(5),cursor_all.getInt(6),paint);
                    }else if(result_list.get(i).equals(B) && result_list.get(i + 1).equals(A)){
                        canvas.drawLine(cursor_all.getInt(3),cursor_all.getInt(4),
                                cursor_all.getInt(5),cursor_all.getInt(6),paint);
                    }
                    cursor_all.moveToNext();
                }catch (Exception e){
                    break;
                }
            }
        }

        //시작지점에 원 추가 panint2 생성해서 다른 형태지정 하기위해서는 따로 값 지정 해놔야함
        //화살표는 나중에

        canvas.drawBitmap(overlay,100,100,paint);

        insideMap_Img.setImageBitmap(bitmap);
    }
}
