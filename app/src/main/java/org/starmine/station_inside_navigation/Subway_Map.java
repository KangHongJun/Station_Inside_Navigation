package org.starmine.station_inside_navigation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import piruincopy.quickaction.ActionItem;
import piruincopy.quickaction.QuickAction;

import static org.starmine.station_inside_navigation.R.drawable.subway_map;

public class Subway_Map extends AppCompatActivity {

    SubsamplingScaleImageView imageView;
    private GestureDetector mDetector;
    private QuickAction quickAction;

    private static String curStation;//선택한 지하철 역 이름
    private static double TileScale;




    private static final int ID_SEARCH = 1;
    private static final int ID_INFO = 2;
    private static final int ID_OK = 3;

    private static int Bookmark_Code;

    private static Cursor cursor_coor;
    private static Cursor bookmark_coor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_map);

        imageView = findViewById(R.id.SubwayMap_Img);
        imageView.setImage(ImageSource.resource(subway_map));
        TileScale = imageView.getMaxScale();

        //이미지 뷰 특정위치로 이동 및 확대 - 이벤트로는 가능하지만, 시작시 안됨
        imageView.setScaleAndCenter(1.1f,new PointF(3900, 3120));

        //Toast.makeText(getApplicationContext(),""+TileScale,Toast.LENGTH_LONG).show();
      

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Map.this,"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        cursor_coor = sqlDB.rawQuery("select * from subway_coordinate",null);




        //클릭 말풍선 테스트
        //배경, 글자자색 지정
        QuickAction.setDefaultColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        QuickAction.setDefaultTextColor(Color.BLACK);

//        //아이템 번호, 모양 지정
//        ActionItem searchItem = new ActionItem(ID_SEARCH, "경로", R.drawable.ic_search);
//        ActionItem infoItem = new ActionItem(ID_INFO, "상세보기", R.drawable.ic_info);
//        ActionItem okItem = new ActionItem(ID_OK, "OK", R.drawable.ic_ok);
//        ActionItem name = new ActionItem(4, "1");
//
//        //말풍선 생성
//        quickAction = new QuickAction(this);
//        quickAction.setColorRes(R.color.purple_200);
//        quickAction.setTextColorRes(R.color.white);
//
//        //말풍선에 아이템 추가
//        quickAction.setTextColor(Color.YELLOW);
//        quickAction.addActionItem(name);
//        quickAction.addActionItem(searchItem);
//        quickAction.addActionItem(infoItem);
//        quickAction.addActionItem(okItem);
//
//        //퀵 액션 클릭 리스너
//        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
//            @Override public void onItemClick(ActionItem item) {
//                //here we can filter which action item was clicked with pos or actionId parameter
//                String title = item.getTitle();
//
//                switch (title){
//                    case "경로":
//                        startActivity(new Intent(Subway_Map.this,Subway_Route.class));
//                        Toast.makeText(getApplicationContext(), curStation, Toast.LENGTH_LONG).show();
//                        break;
//                    case "상세보기":
//                        Intent intent = new Intent(Subway_Map.this,Subway_Detailed_View.class);
//                        intent.putExtra("station",curStation);
//                        startActivity(intent);
//                        //Toast.makeText(getApplicationContext(), cursor_line, Toast.LENGTH_LONG).show();
//                        break;
//                    default:
//                        Toast.makeText(Subway_Map.this, title+" selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                }
//
//                quickAction.show(imageView);
//
//                //Toast.makeText(Subway_Map.this, title+" selected", Toast.LENGTH_SHORT).show();
//                //if (!item.isSticky()) quickAction.remove(item);
//            }
//        });
//


        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Subway_Map_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return false;
            }
        });

        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener(){
            @Override
            public boolean onDown(MotionEvent event) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent event){
                PointF sCoord = imageView.viewToSourceCoord(event.getX(), event.getY());
                int x_cor = (int) sCoord.x;
                int y_cor = (int) sCoord.y;
                //TileScale = 1;

                if (cursor_coor.moveToFirst()){
                    do{
                        if ((x_cor > cursor_coor.getInt(2)*TileScale) && (x_cor < cursor_coor.getInt(4)*TileScale) && (y_cor > cursor_coor.getInt(3)*TileScale) && (y_cor < cursor_coor.getInt(5)*TileScale)) {

                            curStation = cursor_coor.getString(1);
                            makeQuickAction();
                            quickAction.show(imageView,1,1);

                            //Toast.makeText(getApplicationContext(), curStation, Toast.LENGTH_LONG).show();

                        }
                    } while (cursor_coor.moveToNext());

                }

                //배율 얻기, PointF 위치로 화면 이동
                float a = imageView.getScale();
                //Toast.makeText(getApplicationContext(),"x: "+x_cor+ "y :"+y_cor+ "배율"+a,Toast.LENGTH_LONG).show();
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
            case R.id.menu_search:{
                Intent intent = new Intent(Subway_Map.this,Subway_Search.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_setting:{
                Intent intent = new Intent(Subway_Map.this,Options.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeQuickAction(){
        ActionItem searchItem = new ActionItem(ID_SEARCH, "경로", R.drawable.ic_search);
        ActionItem infoItem = new ActionItem(ID_INFO, "상세보기", R.drawable.ic_info);
        ActionItem inner = new ActionItem(5,"역 내부 안내", R.drawable.ic_info);
        ActionItem name = new ActionItem(4, curStation);
        ActionItem bookmark = new ActionItem(ID_OK, "즐겨찾기",R.drawable.empty_star);


        //즐겨찾기 체크하고 별 이미지 바꾸기
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Map.this,"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);
        bookmark_coor = sqlDB.rawQuery("select * from subway_bookmark",null);



        int count = bookmark_coor.getCount();

        //bookmark_coor.moveToFirst();
//        for (int i = 0; i < count; i++) {
//            bookmark_coor.moveToNext();
//
//        }


        for(int i = 0; i < count; i++){
            bookmark_coor.moveToNext();
            //Toast.makeText(getApplicationContext(), "/"+bookmark_coor.getString(0), Toast.LENGTH_SHORT).show();
            if(curStation.equals(bookmark_coor.getString(0))){
                Toast.makeText(getApplicationContext(), "/"+bookmark_coor.getString(0) + "/"+ i, Toast.LENGTH_SHORT).show();
                bookmark = new ActionItem(ID_OK, "즐겨찾기",R.drawable.yellow_star);
                Bookmark_Code = 0;
                break;
            }
            else if (!curStation.equals(bookmark_coor.getString(0))){
                Toast.makeText(getApplicationContext(), "/"+bookmark_coor.getString(0) + "/"+ i, Toast.LENGTH_SHORT).show();
                bookmark = new ActionItem(ID_OK, "즐겨찾기",R.drawable.empty_star);
                Bookmark_Code = 1;
            }
        }




        //말풍선 생성
        quickAction = new QuickAction(this);
        quickAction.setColorRes(R.color.purple_200);

        //말풍선에 아이템 추가
        quickAction.setTextColor(Color.YELLOW);
        quickAction.addActionItem(name);
        quickAction.addActionItem(infoItem);
        quickAction.addActionItem(searchItem);
        quickAction.addActionItem(inner);
        quickAction.addActionItem(bookmark);

        //퀵 액션 클릭 리스너
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override public void onItemClick(ActionItem item) {
                //here we can filter which action item was clicked with pos or actionId parameter
                String title = item.getTitle();

                switch (title){
                    case "경로":
                        startActivity(new Intent(Subway_Map.this,Subway_Route.class));
                        //Toast.makeText(getApplicationContext(), curStation, Toast.LENGTH_LONG).show();
                        break;
                    case "상세보기":
                        Intent intent = new Intent(Subway_Map.this,Subway_Detailed_View.class);
                        intent.putExtra("station",curStation);
                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(), cursor_line, Toast.LENGTH_LONG).show();
                        break;
                    case "역 내부 안내":
                        Intent intent2 = new Intent(Subway_Map.this,Inside_Navigation.class);
                        intent2.putExtra("station",curStation);
                        startActivity(intent2);
                        //Toast.makeText(getApplicationContext(), cursor_line, Toast.LENGTH_LONG).show();
                        break;
                    case "즐겨찾기":
                        if(Bookmark_Code ==1){
                            Insert_Bookmark();
                        }else if (Bookmark_Code ==0){
                            Delete_Bookmark();
                        }
                        //Toast.makeText(getApplicationContext(), ""+Bookmark_Code, Toast.LENGTH_LONG).show();
                    default:
                        //Toast.makeText(Subway_Map.this, title+" selected", Toast.LENGTH_SHORT).show();
                        break;

                }

                quickAction.show(imageView);
                //Toast.makeText(Subway_Map.this, title+" selected", Toast.LENGTH_SHORT).show();
                //if (!item.isSticky()) quickAction.remove(item);
            }
        });
    }


    private void Insert_Bookmark(){
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Map.this,"subway_info.db",null,1);
        sqlDB = Helper.getWritableDatabase();
        Helper.onCreate(sqlDB);

        //Toast.makeText(Subway_Map.this, ""+curStation, Toast.LENGTH_SHORT).show();
        String insertDB = "insert into subway_bookmark values (" + "\"" + curStation + "\"" + ")";
        sqlDB.execSQL(insertDB);
    }

    private void Delete_Bookmark(){
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(Subway_Map.this,"subway_info.db",null,1);
        sqlDB = Helper.getWritableDatabase();
        Helper.onCreate(sqlDB);

        //Toast.makeText(Subway_Map.this, ""+curStation, Toast.LENGTH_SHORT).show();
        String deleteDB = "delete from subway_bookmark where NAME = " + "\"" + curStation + "\"" + "";
        sqlDB.execSQL(deleteDB);




    }
}
