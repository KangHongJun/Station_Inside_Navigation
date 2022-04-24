package fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.starmine.station_inside_navigation.Bookmark;
import org.starmine.station_inside_navigation.DBHelper;
import org.starmine.station_inside_navigation.Inquiry_Page;
import org.starmine.station_inside_navigation.R;
import org.starmine.station_inside_navigation.Subway_Detailed_View;
import org.starmine.station_inside_navigation.Subway_Route;
import org.starmine.station_inside_navigation.Subway_Schedule;

public class Fragment_Detail_line8 extends Fragment {
    private static String curStation;
    Cursor cursor_code;
    static String sqlCode;
    static int code;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.subway_detail,container,false);


        //해당 역 번들 데이터 얻기
        Bundle curstation = getArguments();
        if(curstation != null){
            curStation = curstation.getString("station");
        }

        TextView curSt = viewGroup.findViewById(R.id.Detail_Current_Text);
        curSt.setText(curStation);

        //DB읽기
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(getActivity(),"subway_info.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);


        //현재역 code 얻기
        sqlCode = "select CODE from subway_line where NAME = " +"\""+ curStation +"\""+"and line=8";
        cursor_code = sqlDB.rawQuery(sqlCode,null);
        cursor_code.moveToNext();

        //현재역 code값으로 다음, 이전역 코드 얻기
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

        Button next_btn = viewGroup.findViewById(R.id.Detail_Next_Btn);
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
            }
        });



        Button back_Btn = viewGroup.findViewById(R.id.Detail_Back_Btn);
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
            }
        });



        Button Detail_Route_Btn = viewGroup.findViewById(R.id.Detail_Route_Btn);
        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Subway_Route.class);
                startActivity(intent);
            }
        });




        /* 역 내부안내 만들어지면 수정할 인텐트
        Button Detail_Route_Btn = (Button) findViewById(R.id.Detail_Route_Btn);
        Detail_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Route.class);
                startActivity(intent);
            }
        });*/


        // 시간표 버튼
        Button Deatil_Schedule_Btn = viewGroup.findViewById(R.id.Deatil_Schedule_Btn);
        Deatil_Schedule_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Subway_Schedule.class);
                startActivity(intent);
            }
        });

        Button Detail_Bookmark_Btn = viewGroup.findViewById(R.id.Detail_Bookmark_Btn);
        Detail_Bookmark_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Bookmark.class);
                startActivity(intent);
            }
        });

        //문의하기 버튼
        Button Detail_Inquire_Btn = viewGroup.findViewById(R.id.Detail_Inquire_Btn);
        Detail_Inquire_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Inquiry_Page.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }
}
