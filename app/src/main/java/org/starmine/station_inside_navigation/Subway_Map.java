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

//import me.piruin.quickaction.ActionItem;
//import me.piruin.quickaction.QuickAction;

public class Subway_Map extends AppCompatActivity {

    SubsamplingScaleImageView imageView;
    private GestureDetector mDetector;
    private QuickAction quickAction;

    private static String curStation;//선택한 지하철 역 이름



    private static final int ID_UP = 1;
    private static final int ID_DOWN = 2;
    private static final int ID_SEARCH = 3;
    private static final int ID_INFO = 4;
    private static final int ID_ERASE = 5;
    private static final int ID_OK = 6;
    private static Cursor cursor_coor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_map);

        imageView = findViewById(R.id.SubwayMap_Img);
        imageView.setImage(ImageSource.resource(subway_map));

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

        //아이템 번호, 모양 지정

        ActionItem searchItem = new ActionItem(ID_SEARCH, "경로", R.drawable.ic_search);
        ActionItem infoItem = new ActionItem(ID_INFO, "상세보기", R.drawable.ic_info);
        ActionItem okItem = new ActionItem(ID_OK, "OK", R.drawable.ic_ok);

        //use setSticky(true) to disable QuickAction dialog being dismissed after an item is clicked
        //prevItem.setSticky(true);
        //nextItem.setSticky(true);

        //말풍선 생성
        quickAction = new QuickAction(this);
        quickAction.setColorRes(R.color.purple_200);
        quickAction.setTextColorRes(R.color.white);

        //말풍선에 아이템 추가
        quickAction.setTextColor(Color.YELLOW);
        quickAction.addActionItem(searchItem);
        quickAction.addActionItem(infoItem);
        quickAction.addActionItem(okItem);

        //퀵 액션 클릭 리스너
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override public void onItemClick(ActionItem item) {
                //here we can filter which action item was clicked with pos or actionId parameter
                String title = item.getTitle();

                switch (title){
                    case "경로":
                        startActivity(new Intent(Subway_Map.this,Subway_Route.class));
                        Toast.makeText(getApplicationContext(), curStation, Toast.LENGTH_LONG).show();
                        break;
                    case "상세보기":
                        Intent intent = new Intent(Subway_Map.this,Subway_Detailed_View.class);
                        intent.putExtra("station",curStation);
                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(), cursor_line, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(Subway_Map.this, title+" selected", Toast.LENGTH_SHORT).show();
                        break;

                }

                quickAction.show(imageView);

                //Toast.makeText(Subway_Map.this, title+" selected", Toast.LENGTH_SHORT).show();
                //if (!item.isSticky()) quickAction.remove(item);
            }
        });



        //
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override public void onDismiss() {
                Toast.makeText(Subway_Map.this, "Dismissed", Toast.LENGTH_SHORT).show();
            }
        });



        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Subway_Map_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
       // actionBar.setDisplayShowCustomEnabled(true);
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

                if (cursor_coor.moveToFirst()){
                    do{
                        if ((x_cor > cursor_coor.getInt(2)) && (x_cor < cursor_coor.getInt(4)) && (y_cor > cursor_coor.getInt(3)) && (y_cor < cursor_coor.getInt(5))) {

                            curStation = cursor_coor.getString(1);
                            quickAction.show(imageView,1,1);
                            //Toast.makeText(getApplicationContext(), curStation, Toast.LENGTH_LONG).show();
                        }
                    } while (cursor_coor.moveToNext());

                }

                //Toast.makeText(getApplicationContext(),"x: "+x_cor+ "y :"+y_cor,Toast.LENGTH_LONG).show();
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
}
