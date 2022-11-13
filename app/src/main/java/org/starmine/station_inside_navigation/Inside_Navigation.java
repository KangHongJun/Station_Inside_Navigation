package org.starmine.station_inside_navigation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Inside_Navigation extends AppCompatActivity {
    TextView inside_station;
    static String curStation;
    ImageView StaionInnerMap;
    Bitmap StaionInnerBitmap;
    Button inside_btn;
    ArrayList<Bitmap> DrawCanvas = new ArrayList<Bitmap>();


    int Step = 0;
    int StartIn =0,EndIn=0;
    int floor_cnt = 0;

    ListView listView_floor;
    ArrayAdapter<String> arrayAdapter;

    TextView inside_start,inside_arrival;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_navigation);

        inside_station = (TextView) findViewById(R.id.Inside_Station);

        Intent get_intent = getIntent();
        curStation = get_intent.getStringExtra("station");
        inside_station.setText(curStation);

        inside_start = findViewById(R.id.Inside_Start_Edit);
        inside_arrival = findViewById(R.id.Inside_Arrival_Edit);
        listView_floor = findViewById(R.id.Subway_Floor_List);
        inside_btn = findViewById(R.id.inside_Btn);
        StaionInnerMap = findViewById(R.id.Subway_InnerMap);

        Toolbar toolbar = findViewById(R.id.Inside_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Inside_Navigation.this,"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        //step0 - 역 검색화면 이동
        inside_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Arrival_Subway_Search.class);
                inside_launcher.launch(intent);
                Step = 0;//역 이름 다시입력하기위한 초기화 테스트
                DrawCanvas = new ArrayList<Bitmap>();


            }
        });

        inside_btn.setText("다음");
        inside_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Step==0)
                {
                    System.out.println("입력"+curStation);
                    if (curStation.equals(""))//이거도 그냥 try catch문만 있어도 커버될듯
                        Toast.makeText(getApplicationContext(),"역 이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    else
                    {
                        try {

                            Cursor cursor1 = sqlDB.rawQuery("select Max(B) from BeomB2_test3",null);
                            String quety = "select Max(Floor_Nm) from Floor_TB where Station_Name=" + "\""+curStation+"\"";
                            Cursor cursor_Floor = sqlDB.rawQuery(quety,null);


                            cursor1.moveToLast();
                            cursor_Floor.moveToLast();
                            if(cursor1 != null){
                                String test  =cursor_Floor.getString(0);
                                test = test.replaceAll("[^0-9]", "");
                                //floor_cnt = cursor1.getInt(0)/100;
                                floor_cnt = Integer.parseInt(test);
                            }



                            String curfloor = "B2";
                            String sql = "Select Floor_Image from Floor_TB Where Station_Name = '" + curStation + "' AND Floor_Nm = '" + curfloor + "'";
                            Cursor Floor_cursor = sqlDB.rawQuery(sql,null);

                            Floor_cursor.moveToLast();
                            byte[] Image = Floor_cursor.getBlob(0);

                            StaionInnerBitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length).copy(Bitmap.Config.ARGB_8888, true);

                            StaionInnerMap.setImageBitmap(StaionInnerBitmap);
                            DrawCanvas.add(0,StaionInnerBitmap);
                            Step=1;

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"잘못된 역 이름입니다.",Toast.LENGTH_SHORT).show();
                        }

                    }


                }else if(Step==1)
                {
                    try {
                        System.out.println("Sdsdd");
                        String curfloor = "B1";
                        String sql = "Select Floor_Image from Floor_TB Where Station_Name = '" + curStation + "' AND Floor_Nm = '" + curfloor + "'";
                        Cursor Floor_cursor = sqlDB.rawQuery(sql,null);

                        Floor_cursor.moveToLast();
                        byte[] Image = Floor_cursor.getBlob(0);

                        StaionInnerBitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length).copy(Bitmap.Config.ARGB_8888, true);
                        StaionInnerMap.setImageBitmap(StaionInnerBitmap);
                        DrawCanvas.add(1,StaionInnerBitmap);
                        Step=2;

                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"출발지를 입력 해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }else if(Step==2)
                {
                    if(inside_arrival.length()==0)
                    {
                        Toast.makeText(getApplicationContext(),"목적지를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else {
                        System.out.println(floor_cnt+"floor");
                        ListAdd(floor_cnt);
                        //SetDrawLine(DrawCanvas.get(0),floor_cnt);
                    }

                }

            }
        });

        listView_floor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String curFloor = adapterView.getItemAtPosition(i).toString();
                String curfloor = curFloor.replaceAll("[^0-9]", "");


                int cur = Integer.parseInt(curfloor);
                System.out.println(cur+"cur-----");
                if (cur==2){
                    SetDrawLine(DrawCanvas.get(0),2);
                }else if (cur==1)
                    SetDrawLine(DrawCanvas.get(1),1);


            }
        });







    }

    private void ListAdd(int Cnt){
        ArrayList<String> itemList = new ArrayList<>();

        for(int i=1; i <= Cnt; i++){
            itemList.add("B".concat(String.valueOf(i)));
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
        listView_floor.setAdapter(arrayAdapter);
    }

    ActivityResultLauncher<Intent> inside_launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult data) {
                    if (data.getResultCode() == Activity.RESULT_OK){
                        Intent intent = data.getData();
                        String result = intent.getStringExtra("result");

                        curStation = result;
                        inside_station.setText(result);


                    }
                }
            });

    public void setStartText(String num){
        inside_start.setText(num);
    }

    public void setStartSubText(String num){
        inside_arrival.setText(num);
    }

    //메뉴 적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.inside_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
            case R.id.menu_map:{
                Intent intent = new Intent(Inside_Navigation.this,Subway_Map.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_detail:{
                Intent intent = new Intent(Inside_Navigation.this,Subway_Detailed_View.class);
                intent.putExtra("station",inside_station.getText().toString());
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void SetDrawLine(Bitmap bitmap, int Curfloor){

        //캔버스 나중엔 위에서 설정..이 가능할지는 모르겠다.
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,new Matrix(),null);

        System.out.println("SetDrawLine"+Curfloor);
        System.out.println("bitmap"+bitmap);


        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Inside_Navigation.this,"test.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        Cursor cursor_all = sqlDB.rawQuery("select * from BeomB2_test3;",null);
        int NodeCount = cursor_all.getCount();

        Cursor cursor_get_node = sqlDB.rawQuery("select max(B/100) from BeomB2_test3;",null);

        cursor_get_node.moveToFirst();
        int FloorCount = cursor_get_node.getInt(0);

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
            //System.out.println(floorNodeCount+"floorNodeCount");
            int Mnode = 100*(i)-floorNodeCount.get(i-1);


            cursor_test.moveToFirst();
            for (int j=0;j<cursor_test.getCount();j++){
                if (cursor_test.getInt(1)>=(i+1)*100){
                    int Mnode2 = 100*(i+1)-floorNodeCount.get(i);
                    navigation.input(cursor_test.getInt(0)-Mnode,cursor_test.getInt(1)-Mnode2,cursor_test.getInt(2));
//                    System.out.println(cursor_test.getInt(0)-Mnode+"if일때 A");
//                    System.out.println(cursor_test.getInt(1)-Mnode2+"if일때 B");
                }else {
                    navigation.input(cursor_test.getInt(0)-Mnode,cursor_test.getInt(1)-Mnode,cursor_test.getInt(2));
//                    System.out.println(cursor_test.getInt(0)-Mnode+" A");
//                    System.out.println(cursor_test.getInt(1)-Mnode+" B");
                }
                cursor_test.moveToNext();
            }
        }
        //루트 반환, 선 그리기
        StartIn  = Integer.parseInt(String.valueOf(inside_start.getText()));
        EndIn  = Integer.parseInt(String.valueOf(inside_arrival.getText()));

        //이 부분 수정 필요 - 직접입력한 노드번호가 지하2층이 100번대임
        //StartIn,EndIn서로 뒤에 붙는 값 바꾸고, floorNodeCount.get(1)조정
        StartIn= StartIn-1;
        EndIn = EndIn+floorNodeCount.get(1)-1;
        navigation.dijkstra(StartIn,EndIn);

        ArrayList route_list = navigation.getRoute_list();

        //노드값 조정
        ArrayList<Integer> result_list = new ArrayList<>();
        for(int i=0;i<route_list.size();i++) {
            //System.out.println(route_list.get(i));
            int route_list_value = (int) route_list.get(i);
            if (route_list_value > floorNodeCount.get(FloorCount - Curfloor) && route_list_value < floorNodeCount.get(FloorCount - Curfloor + 1)) {
                int result = (int) route_list.get(i) - floorNodeCount.get(FloorCount - Curfloor) + 100 * (FloorCount - Curfloor + 1);
                result_list.add(result);
            }
        }

        Bitmap overlay = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);

        //그림그리기


        for(int i=0;i<result_list.size();i++) {
            cursor_all.moveToFirst();

            while (true) {
                try {
                    int A = cursor_all.getInt(0);
                    int B = cursor_all.getInt(1);
                    //A,B또는 B,A와 같은지 체크
                    if (result_list.get(i).equals(A) && result_list.get(i + 1).equals(B)) {
                        canvas.drawLine(cursor_all.getInt(3), cursor_all.getInt(4),
                                cursor_all.getInt(5), cursor_all.getInt(6), paint);

                    } else if (result_list.get(i).equals(B) && result_list.get(i + 1).equals(A)) {
                        canvas.drawLine(cursor_all.getInt(3), cursor_all.getInt(4),
                                cursor_all.getInt(5), cursor_all.getInt(6), paint);
                    }
                    cursor_all.moveToNext();
                } catch (Exception e) {
                    break;
                }
            }
        }

        canvas.drawBitmap(overlay,100,100,paint);
        StaionInnerMap.setImageBitmap(bitmap);
    }
}
