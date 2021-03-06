package fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.starmine.station_inside_navigation.DBHelper;
import org.starmine.station_inside_navigation.DatabaseHelper;
import org.starmine.station_inside_navigation.Inquiry_Page;
import org.starmine.station_inside_navigation.Inside_Navigation;
import org.starmine.station_inside_navigation.R;
import org.starmine.station_inside_navigation.Subway_Detailed_View;
import org.starmine.station_inside_navigation.Subway_Route;
import org.starmine.station_inside_navigation.Subway_Schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Fragment_Detail_line7 extends Fragment {
    static ViewGroup viewGroup;
    TextView Arrival_L1;
    TextView Arrival_L2;
    TextView Arrival_R1;
    TextView Arrival_R2;

    private static String curStation;
    Cursor cursor_code;
    static String sqlCode;
    static int code;
    DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.subway_detail,container,false);
        Arrival_L1 = viewGroup.findViewById(R.id.Detail_LTime_Text);
        Arrival_L2 = viewGroup.findViewById(R.id.Detail_LNextTime_Text);
        Arrival_R1 = viewGroup.findViewById(R.id.Detail_RTime_Text);
        Arrival_R2 = viewGroup.findViewById(R.id.Detail_RNextTime_Text);


        //?????? ??? ?????? ????????? ??????
        Bundle curstation = getArguments();
        if(curstation != null){
            curStation = curstation.getString("station");
        }

        db = new DatabaseHelper(getActivity());
        TextView curSt = viewGroup.findViewById(R.id.Detail_Current_Text);
        curSt.setText(curStation);
        curSt.setBackgroundResource(R.drawable.station7_main);

        //DB??????
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);


        //????????? code ??????
        sqlCode = "select CODE from subway_line where NAME = " +"\""+ curStation +"\""+"and line=7";
        cursor_code = sqlDB.rawQuery(sqlCode,null);
        cursor_code.moveToNext();

        //????????? code????????? ??????, ????????? ?????? ??????
        code = cursor_code.getInt(0);
        int nextCode = code-1;
        int beforeCode = code+1;


        String beforeStation = "select NAME from subway_line where CODE = " + beforeCode +"";
        cursor_code = sqlDB.rawQuery(beforeStation,null);
        cursor_code.moveToNext();

        TextView beforeSt = viewGroup.findViewById(R.id.Detail_Back_Btn);
        beforeSt.setText(cursor_code.getString(0));

        String nextStation = "select NAME from subway_line where CODE = " + nextCode +"";
        cursor_code = sqlDB.rawQuery(nextStation,null);
        cursor_code.moveToNext();

        TextView nextSt = viewGroup.findViewById(R.id.Detail_Next_Btn);
        nextSt.setText(cursor_code.getString(0));

        //???????????? ??????
        setArrivalTime();

        Button next_btn = viewGroup.findViewById(R.id.Detail_Next_Btn);
        next_btn.setBackgroundResource(R.drawable.station7_sub);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = code-1;
                int nextCode = code-1;
                int beforeCode = code+1;

                String curStation = "select NAME from subway_line where CODE = " + code +"";
                cursor_code = sqlDB.rawQuery(curStation,null);
                cursor_code.moveToNext();

                TextView curSt = viewGroup.findViewById(R.id.Detail_Current_Text);
                curSt.setText(cursor_code.getString(0));

                ((Subway_Detailed_View)getActivity()).Update_tab(cursor_code.getString(0));

                String nextStation = "select NAME from subway_line where CODE = " + nextCode +"";
                cursor_code = sqlDB.rawQuery(nextStation,null);
                cursor_code.moveToNext();

                TextView nextSt = viewGroup.findViewById(R.id.Detail_Next_Btn);
                nextSt.setText(cursor_code.getString(0));

                String beforeStation = "select NAME from subway_line where CODE = " + beforeCode +"";
                cursor_code = sqlDB.rawQuery(beforeStation,null);
                cursor_code.moveToNext();

                String befroe = cursor_code.getString(0);
                TextView beforeSt = viewGroup.findViewById(R.id.Detail_Back_Btn);
                beforeSt.setText(befroe);

                //???????????? ??????
                setArrivalTime();
            }
        });



        Button back_Btn = viewGroup.findViewById(R.id.Detail_Back_Btn);
        back_Btn.setBackgroundResource(R.drawable.station7_sub);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = code+1;
                int nextCode = code-1;
                int beforeCode = code+1;

                String curStation = "select NAME from subway_line where CODE = " + code +"";
                cursor_code = sqlDB.rawQuery(curStation,null);
                cursor_code.moveToNext();

                TextView curSt = viewGroup.findViewById(R.id.Detail_Current_Text);
                curSt.setText(cursor_code.getString(0));

                ((Subway_Detailed_View)getActivity()).Update_tab(cursor_code.getString(0));

                String nextStation = "select NAME from subway_line where CODE = " + nextCode +"";
                cursor_code = sqlDB.rawQuery(nextStation,null);
                cursor_code.moveToNext();

                TextView nextSt = viewGroup.findViewById(R.id.Detail_Next_Btn);
                nextSt.setText(cursor_code.getString(0));

                String beforeStation = "select NAME from subway_line where CODE = " + beforeCode +"";
                cursor_code = sqlDB.rawQuery(beforeStation,null);
                cursor_code.moveToNext();

                String before = cursor_code.getString(0);
                TextView beforeSt = viewGroup.findViewById(R.id.Detail_Back_Btn);
                beforeSt.setText(before);

                //???????????? ??????
                setArrivalTime();
            }
        });


        Button Detail_Route_Btn = viewGroup.findViewById(R.id.Detail_Route_Btn);
        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle curstation = getArguments();
                if(curstation != null){
                    curStation = curstation.getString("station");
                }
                Intent intent = new Intent(getActivity(), Subway_Route.class);
                intent.putExtra("station", curStation);
                startActivity(intent);
            }
        });




        Button Detail_Inner_Btn = viewGroup.findViewById(R.id.Detail_Inner_Btn);
        Detail_Inner_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle curstation = getArguments();
                if(curstation != null){
                    curStation = curstation.getString("station");
                }
                Intent intent = new Intent(getActivity(), Inside_Navigation.class);
                intent.putExtra("station", curStation);
                startActivity(intent);
            }
        });


        // ????????? ??????
        Button Deatil_Schedule_Btn = viewGroup.findViewById(R.id.Deatil_Schedule_Btn);
        Deatil_Schedule_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle curstation = getArguments();
                if(curstation != null){
                    curStation = curstation.getString("station");
                }

                Intent intent = new Intent(getActivity(),Subway_Schedule.class);
                intent.putExtra("station",curStation+"7");
                startActivity(intent);
            }
        });

        //???????????? ??????
        Button Detail_Bookmark_Btn = viewGroup.findViewById(R.id.Detail_Bookmark_Btn);
        Bookmarkbtnimage(curStation);

        Detail_Bookmark_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int num = 0;
                Bundle curstation = getArguments();
                if(curstation != null){
                    curStation = curstation.getString("station");
                }

                num = db.BookmarkBtn(curStation);

                if (num == 1) {
                    db.deleteBookmark(curStation);
                    db.insertData(curStation);

                    Bookmarkbtnimage(curStation);
                }

                if (num == 0) {
                    db.insertBookmark(curStation);
                    db.deleteData(curStation);

                    Bookmarkbtnimage(curStation);
                }
            }
        });

        //???????????? ??????
        Button Detail_Inquire_Btn = viewGroup.findViewById(R.id.Detail_Inquire_Btn);
        Detail_Inquire_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Inquiry_Page.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }
    //???????????? ??????
    public void setArrivalTime(){
        //????????? ??????
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnect = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        if(isConnect){
            //????????? ????????????
            setArrivalRealTime();
            //Toast.makeText(getContext(),"?????????",Toast.LENGTH_SHORT).show();

        }else if (!isConnect){
            //????????? ?????? ????????????
            setUPArrivalTime();
            setDOWNArrivalTime();
            Toast.makeText(getContext(),"?????????",Toast.LENGTH_SHORT).show();
        }

    }

    //????????? ?????? ??????
    public void setArrivalRealTime(){

        Bundle curstation = getArguments();
        if(curstation != null){
            curStation = curstation.getString("station");
        }

        //????????? ???????????? ?????????
        new Thread(){
            @Override
            public void run() {

                try {
                    String URL = "https://m.search.naver.com/search.naver?sm=tab_hty.top&where=m&query="+curStation+"7??????";

                    System.out.println(URL);
                    Document doc;
                    doc = Jsoup.connect(URL).get();
                    //??????1
                    Elements up_minute1 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(1) > ul > li:nth-child(1) > span.count > em");

                    Elements up_dir1 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(1) > ul > li:nth-child(1) > span.time_box > span");
                    //??????2
                    Elements up_minute2 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(1) > ul > li:nth-child(2) > span.count > em");

                    Elements up_dir2 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(1) > ul > li:nth-child(2) > span.time_box > span");

                    //??????1
                    Elements down_minute1 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(2) > ul > li:nth-child(1) > span.count > em");

                    Elements down_dir1 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(2) > ul > li:nth-child(1) > span.time_box > span");

                    //??????2
                    Elements down_minute2 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(2) > ul > li:nth-child(2) > span.count > em");

                    Elements down_dir2 = doc.select("#ct > section.sc._sc_subway.mcs_subway > div.api_subject_bx > div.subway > div.station_info_top > div.arrive_area._arrive_area > div > div:nth-child(2) > ul > li:nth-child(2) > span.time_box > span");

                    String realTime1 = up_dir1.get(0).text()+ "??? " + up_minute1.get(0).text() + "???";
                    String realTime2 = up_dir2.get(0).text()+ "??? " + up_minute2.get(0).text() + "???";
                    String realTime3 = down_dir1.get(0).text()+ "??? " + down_minute1.get(0).text() + "???";
                    String realTime4 = down_dir2.get(0).text()+ "??? " + down_minute2.get(0).text() + "???";

                    String realTime = realTime1+"/"+realTime2+"/"+realTime3+"/"+realTime4;

                    Message message = Message.obtain();
                    message.obj = realTime;
                    handler.sendMessage(message);


                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //????????? ????????? - ????????? ???????????? ??????
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String time = (String) message.obj;
            String UP_array[] = time.split("/");
            Arrival_L1.setText(UP_array[0]);
            Arrival_L2.setText(UP_array[1]);
            Arrival_R1.setText(UP_array[2]);
            Arrival_R2.setText(UP_array[3]);


            return true;
        }
    });

    //???????????? ????????? ??????
    //?????? ?????? ?????????
    public void setUPArrivalTime(){
        int min = -1 ;

        Bundle curstation = getArguments();
        if(curstation != null){
            curStation = curstation.getString("station")+7;
        }

        //????????????
        long curTime = System.currentTimeMillis();
        Date date = new Date(curTime);

        TimeZone timezone;
        timezone = TimeZone.getTimeZone("Asia/Seoul");


        //?????? ???
        SimpleDateFormat dateFormat = new SimpleDateFormat("k", Locale.KOREAN);
        dateFormat.setTimeZone(timezone);
        String curHour = dateFormat.format(date);

        SimpleDateFormat dateFormat_MM = new SimpleDateFormat("mm", Locale.KOREAN);
        dateFormat_MM.setTimeZone(timezone);
        String curMinute = dateFormat_MM.format(date);

        SimpleDateFormat dateFormat_SS = new SimpleDateFormat("ss", Locale.KOREAN);
        dateFormat_SS.setTimeZone(timezone);
        String curSecond = dateFormat_SS.format(date);


        SimpleDateFormat dateFormat_QQ = new SimpleDateFormat("k:mm:ss", Locale.KOREAN);
        dateFormat_QQ.setTimeZone(timezone);

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"subway_schedule.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        //curStation = "??????4";

        String sqlCode = "select * from schedule where NAME = " +"\""+ curStation +"\""+"and TYPE=0";
        Cursor UP_cursor = sqlDB.rawQuery(sqlCode,null);

        int UP_count = UP_cursor.getCount();
        String[] UP_array = new String[100];
        String UP_nlist = null;
        String UP_tlist = null;
        String[] UP_text = new String[100];

        int time = Integer.parseInt(curHour)-2;
        int minute = Integer.parseInt(curMinute);

        //?????? ?????????
        UP_cursor.moveToFirst();
        for(int i = 0; i < UP_count; i++){
            UP_nlist = UP_cursor.getString(time).replaceAll("[^0-9]", " ");
            UP_cursor.moveToNext();
        }
        UP_nlist = UP_nlist.replaceAll("\\s+", " ");
        UP_array = UP_nlist.split(" ");


        //?????? ?????????
        UP_cursor.moveToFirst();
        for(int i = 0; i < UP_count; i++){
            UP_tlist = UP_cursor.getString(time).replaceAll("[0-9]", " ");
            UP_cursor.moveToNext();
        }
        UP_tlist = UP_tlist.replaceAll("\\s+", " ");
        UP_text = UP_tlist.split(" ");


        String nearTime=curHour;
        int second = 60 - Integer.parseInt(curSecond);


        //?????? ?????? ?????? - ??? ??????
        for(int i =0; i<UP_array.length;i++) {
            //??? ??????
            if (minute < Integer.parseInt(UP_array[i])) {
                min = Integer.parseInt(UP_array[i]) - Integer.parseInt(curMinute)-1;
                Arrival_L1 = viewGroup.findViewById(R.id.Detail_LTime_Text);
                Arrival_L1.setText(UP_text[i + 1] + "??? " + min + "??? " + second + "???");
                if (i + 1 != UP_array.length) {
                    min = Integer.parseInt(UP_array[i + 1]) - Integer.parseInt(curMinute) - 1;
                    Arrival_L2 = viewGroup.findViewById(R.id.Detail_LNextTime_Text);
                    Arrival_L2.setText(UP_text[i + 1] + "??? " + min + "??? " + second + "???");
                    break;
                } else {
                    UP_cursor.moveToFirst();
                    for (int j = 0; j < UP_count; j++) {
                        UP_nlist = UP_cursor.getString(time + 1).replaceAll("[^0-9]", " ");
                        UP_cursor.moveToNext();
                    }
                    UP_nlist = UP_nlist.replaceAll("\\s+", " ");
                    UP_array = UP_nlist.split(" ");
                    min = Integer.parseInt(UP_array[0]) + 60 - Integer.parseInt(curMinute) - 1;

                    Arrival_L2 = viewGroup.findViewById(R.id.Detail_LTime_Text);
                    Arrival_L2.setText(UP_text[i + 1] + "??? " + min + "??? " + second + "???");
                    break;
                }
            }else if (i == UP_array.length && min == -1) {
                UP_cursor.moveToFirst();
                for (int j = 0; j < UP_count; j++) {
                    UP_nlist = UP_cursor.getString(time + 1).replaceAll("[^0-9]", " ");
                    UP_cursor.moveToNext();
                }
                UP_nlist = UP_nlist.replaceAll("\\s+", " ");
                UP_array = UP_nlist.split(" ");
                min = Integer.parseInt(UP_array[0]) + 60 - Integer.parseInt(curMinute) - 1;

                Arrival_L1 = viewGroup.findViewById(R.id.Detail_LTime_Text);
                Arrival_L1.setText(UP_text[i + 1] + "??? " + min + "??? " + second + "???\n" + minute + "\n" + UP_array[0]);
                break;
            }
        }
    }

    //?????? ????????????
    public void setDOWNArrivalTime(){
        int min = -1 ;

        Bundle curstation = getArguments();
        if(curstation != null){
            curStation = curstation.getString("station")+7;
        }

        //????????????
        long curTime = System.currentTimeMillis();
        Date date = new Date(curTime);

        TimeZone timezone;
        timezone = TimeZone.getTimeZone("Asia/Seoul");

        //?????? ???
        SimpleDateFormat dateFormat = new SimpleDateFormat("k", Locale.KOREAN);
        dateFormat.setTimeZone(timezone);
        String curHour = dateFormat.format(date);

        SimpleDateFormat dateFormat_MM = new SimpleDateFormat("mm", Locale.KOREAN);
        dateFormat_MM.setTimeZone(timezone);
        String curMinute = dateFormat_MM.format(date);

        SimpleDateFormat dateFormat_SS = new SimpleDateFormat("ss", Locale.KOREAN);
        dateFormat_SS.setTimeZone(timezone);
        String curSecond = dateFormat_SS.format(date);


        SimpleDateFormat dateFormat_QQ = new SimpleDateFormat("k:mm:ss", Locale.KOREAN);
        dateFormat_QQ.setTimeZone(timezone);

        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"subway_schedule.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);

        //curStation = "??????4";
        //?????? test
        String sqlCode = "select * from schedule where NAME = " +"\""+ curStation +"\""+"and TYPE=1";
        Cursor DOWN_cursor = sqlDB.rawQuery(sqlCode,null);

        int DOWN_count = DOWN_cursor.getCount();

        String[] DOWN_array = new String[100];
        String DOWN_nlist = null;
        String DOWN_tlist = null;
        String[] DOWN_text = new String[100];

        int time = Integer.parseInt(curHour)-2;
        int minute = Integer.parseInt(curMinute);

        //?????? ?????????
        DOWN_cursor.moveToFirst();
        for(int i = 0; i < DOWN_count; i++){
            DOWN_nlist = DOWN_cursor.getString(time).replaceAll("[^0-9]", " ");
            DOWN_cursor.moveToNext();
        }
        DOWN_nlist = DOWN_nlist.replaceAll("\\s+", " ");
        DOWN_array = DOWN_nlist.split(" ");

        //?????? ?????????
        DOWN_cursor.moveToFirst();
        for(int i = 0; i < DOWN_count; i++){
            DOWN_tlist = DOWN_cursor.getString(time).replaceAll("[0-9]", " ");
            DOWN_cursor.moveToNext();
        }
        DOWN_tlist = DOWN_tlist.replaceAll("\\s+", " ");
        DOWN_text = DOWN_tlist.split(" ");


        String nearTime=curHour;
        int second = 60 - Integer.parseInt(curSecond);


        //?????? ?????? ?????? - ??? ??????
        for(int i =0; i<DOWN_array.length;i++) {
            if (minute < Integer.parseInt(DOWN_array[i])) {
                min = Integer.parseInt(DOWN_array[i]) - Integer.parseInt(curMinute)-1;
                Arrival_R1 = viewGroup.findViewById(R.id.Detail_RTime_Text);
                Arrival_R1.setText(DOWN_text[i + 1] + "??? " + min + "??? " + second + "???");
                if (i + 1 != DOWN_array.length) {
                    min = Integer.parseInt(DOWN_array[i + 1]) - Integer.parseInt(curMinute) - 1;
                    Arrival_R2 = viewGroup.findViewById(R.id.Detail_RNextTime_Text);
                    Arrival_R2.setText(DOWN_text[i + 1] + "??? " + min + "??? " + second + "???");
                    break;
                } else {
                    DOWN_cursor.moveToFirst();
                    for (int j = 0; j < DOWN_count; j++) {
                        DOWN_nlist = DOWN_cursor.getString(time + 1).replaceAll("[^0-9]", " ");
                        DOWN_cursor.moveToNext();
                    }
                    DOWN_nlist = DOWN_nlist.replaceAll("\\s+", " ");
                    DOWN_array = DOWN_nlist.split(" ");
                    min = Integer.parseInt(DOWN_array[0]) + 60 - Integer.parseInt(curMinute) - 1;

                    Arrival_R2 = viewGroup.findViewById(R.id.Detail_RNextTime_Text);
                    Arrival_R2.setText(DOWN_text[i + 1] + "??? " + min + "??? " + second + "???");
                    break;
                }
            } else if (i == DOWN_array.length) { //minute < Integer.parseInt(DOWN_array[i] ?????? ????????????????
                DOWN_cursor.moveToFirst();
                for (int j = 0; j < DOWN_count; j++) {
                    DOWN_nlist = DOWN_cursor.getString(time + 1).replaceAll("[^0-9]", " ");
                    DOWN_cursor.moveToNext();
                }
                DOWN_nlist = DOWN_nlist.replaceAll("\\s+", " ");
                DOWN_array = DOWN_nlist.split(" ");
                min = Integer.parseInt(DOWN_array[0]) + 60 - Integer.parseInt(curMinute) - 1;

                Arrival_R1 = viewGroup.findViewById(R.id.Detail_RTime_Text);
                Arrival_R1.setText(DOWN_text[i + 1] + "??? " + min + "??? " + second + "???\n" + minute + "\n" + DOWN_array[0]);

                min = Integer.parseInt(DOWN_array[1]) + 60 - Integer.parseInt(curMinute) - 1;
                Arrival_R2 = viewGroup.findViewById(R.id.Detail_RNextTime_Text);
                Arrival_R2.setText(DOWN_text[i + 1] + "??? " + min + "??? " + second + "???");
                break;
            }
        }
    }

    private void Bookmarkbtnimage(String curStation){
        Button Detail_Bookmark_Btn = viewGroup.findViewById(R.id.Detail_Bookmark_Btn);
        int num = db.BookmarkBtn(curStation);

        if (num == 1){
            Detail_Bookmark_Btn.setBackgroundResource(R.drawable.yellow_star);
        }

        if (num == 0){
            Detail_Bookmark_Btn.setBackgroundResource(R.drawable.empty_star);
        }
    }
}
