package fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.starmine.station_inside_navigation.DBHelper;
import org.starmine.station_inside_navigation.R;
import org.starmine.station_inside_navigation.Schedule;
import org.starmine.station_inside_navigation.ScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Fragment_Weekday extends Fragment {
    ViewGroup viewGroup;
    static String UP_schedule = "";
    static String DOWN_schedule = "";
    static String curStation;
    static String setTime;

    static int UP_count;
    static int DOWN_count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.test_schedule,container,false);

        RecyclerView recyclerView = viewGroup.findViewById(R.id.testCycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        ScheduleAdapter adapter = new ScheduleAdapter();


        for (int i=3;i<22;i++){
            setUPSchedule(i);
            setDOWNSchedule(i);
            adapter.addItem(new Schedule(UP_schedule,DOWN_schedule));
        }

        recyclerView.setAdapter(adapter);

        //현재 시간으로 스크롤 이동
        LinearLayoutManager recyclerManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        long curTime = System.currentTimeMillis();
        Date date = new Date(curTime);
        TimeZone timezone;
        timezone = TimeZone.getTimeZone("Asia/Seoul");
        SimpleDateFormat dateFormat = new SimpleDateFormat("k", Locale.KOREAN);
        dateFormat.setTimeZone(timezone);
        String curHour = dateFormat.format(date);
        recyclerManager.scrollToPositionWithOffset(Integer.parseInt(curHour)-5,0);






//        TextView UP_textView = viewGroup.findViewById(R.id.UP_text);
//        UP_textView.setText(UP_schedule);
//
//        TextView DOWN_textView = viewGroup.findViewById(R.id.DOWN_text);
//        DOWN_textView.setText(DOWN_schedule);

        return viewGroup;
    }


    private void setUPSchedule(int time){
        UP_schedule = "";
        Bundle curstation_bundle = getArguments();
        if(curstation_bundle != null){
            curStation = curstation_bundle.getString("station");
        }

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"subway_schedule.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        //상행
        String sqlCode = "select * from schedule where NAME = " +"\""+ curStation +"\""+"and TYPE=0";
        Cursor UP_cursor = sqlDB.rawQuery(sqlCode,null);


        UP_count = UP_cursor.getCount();
        String[] UP_array = new String[100];
        String UP_nlist = null;
        String UP_tlist = null;
        String[] UP_text = new String[100];

        UP_cursor.moveToFirst();
        for(int i = 0; i < UP_count; i++){
            UP_nlist = UP_cursor.getString(time).replaceAll("[^0-9]", " ");
            UP_cursor.moveToNext();
        }
        if(UP_count!=0){
            UP_nlist = UP_nlist.replaceAll("\\s+", " ");
            UP_array = UP_nlist.split(" ");
        }


        UP_cursor.moveToFirst();
        for(int i = 0; i < UP_count; i++){
            UP_tlist = UP_cursor.getString(time).replaceAll("[0-9]", " ");
            UP_cursor.moveToNext();
        }
        UP_tlist = UP_tlist.replaceAll("\\s+", " ");
        UP_text = UP_tlist.split(" ");

        setTime = "";
        setTime = String.valueOf(time +2);
        if(time<7){
            setTime = "0"+setTime;
        }

        //텍스트 세팅할 String 만들기
        for(int i=0;i<UP_array.length;i++){
            if(UP_text[i + 1].equals("없음")){
                UP_schedule = UP_text[i+1] + "\n\n";
            }else
            UP_schedule = UP_schedule + setTime + " : " +  UP_array[i]+ " " + UP_text[i+1] + "\n\n";
        }
    }

    private void setDOWNSchedule(int time){
        DOWN_schedule = "";
        Bundle curstation_bundle = getArguments();
        if(curstation_bundle != null){
            curStation = curstation_bundle.getString("station");
        }


        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"subway_schedule.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        //하행
        String sqlCode = "select * from schedule where NAME = " +"\""+ curStation +"\""+"and TYPE=1";
        Cursor DOWN_cursor = sqlDB.rawQuery(sqlCode,null);

        DOWN_count = DOWN_cursor.getCount();
        String[] DOWN_array = new String[100];
        String DOWN_nlist = null;
        String DOWN_tlist = null;

        String[] DOWN_text = new String[100];

        DOWN_cursor.moveToFirst();
        for(int i = 0; i < DOWN_count; i++){
            DOWN_nlist = DOWN_cursor.getString(time).replaceAll("[^0-9]", " ");
            DOWN_cursor.moveToNext();
        }
        DOWN_nlist = DOWN_nlist.replaceAll("\\s+", " ");
        DOWN_array = DOWN_nlist.split(" ");

        DOWN_cursor.moveToFirst();
        for(int i = 0; i < DOWN_count; i++){
            DOWN_tlist = DOWN_cursor.getString(time).replaceAll("[0-9]", " ");
            DOWN_cursor.moveToNext();
        }
        DOWN_tlist = DOWN_tlist.replaceAll("\\s+", " ");
        DOWN_text = DOWN_tlist.split(" ");

        setTime = "";
        setTime = String.valueOf(time +2);
        if(time<7){
            setTime = "0"+setTime;
        }

        //텍스트 세팅할 String 만들기
        for(int i=0;i<DOWN_array.length;i++){
            if(DOWN_text[i + 1].equals("없음")){
                DOWN_schedule = DOWN_text[i+1] + "\n\n";
            }else
                DOWN_schedule = DOWN_schedule + setTime + " : " +  DOWN_array[i]+ " " + DOWN_text[i+1] + "\n\n";
        }
    }
}
