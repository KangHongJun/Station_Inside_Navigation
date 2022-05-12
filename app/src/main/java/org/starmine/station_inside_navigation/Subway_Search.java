package org.starmine.station_inside_navigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Subway_Search extends AppCompatActivity {
    SearchView searchView;
    ListView listView_search, listView_history, listView_bookmark;
    DatabaseHelper db;
    String[] subwayList = {"가능","가락시장","가산디지털단지","가양","가재울","가정","가정중앙시장","강남","강남구청","강동","강동구청","강변","강일","개화","개화산","건대입구","검단사거리","검단오류","검바위","검암","경마공원","경복궁","경찰병원","고덕","고려대","고속터미널","고잔","공덕","공릉","공항시장","과천","관악","광나루","광명사거리","광운대","광화문","광흥창","교대","구로","구로디지털단지","구반포","구산","구의","구파발","국회의사당","군자","군포","굴포천","굽은다리","금정","금천구청","금호","길동","길음","김포공항","까치산","까치울","낙성대","남구로","남동구청","남부터미널","남성","남영","남위례","남태령","남한산성입구","내방","노들","노량진","노원","녹번","녹사평","녹양","녹천","논현","단대오거리","답십리","당고개","당산","당정","대곡","대공원","대림","대방","대야미","대청","대치","대화","대흥","덕계","덕정","도곡","도봉","도봉산","독립문","독바위","독산","독정","돌곶이","동대문","동대문역사문화공원","동대입구","동두천","동두천중앙","동묘앞","동작","두정","둔촌오륜","등촌","디지털미디어시티","뚝섬","뚝섬유원지","마곡","마곡나루","마두","마들","마장","마전","마포","마포구청","만수","망원","망월사","매봉","먹골","면목","명동","명일","명학","모란","모래내시장","목동","몽촌토성","무악재","문래","문정","미사","미아","미아사거리","반월","반포","발산","방배","방학","방화","배방","백석","버티고개","범계","별내별가람","병점","보라매","보문","보산","복정","봉명","봉은사","봉천","봉화산","부천시청","부천종합운동장","부평구청","불광","불광","사가정","사당","사평","산곡","산본","산성","삼각지","삼산체육관","삼성","삼성중앙","삼송","삼전","상계","상도","상동","상록수","상봉","상수","상왕십리","상월곡","상일동","새절","샛강","서구청","서대문","서부여성회관","서울대입구","서울역","서정리","서초","석계","석남","석바위시장","석수","석천사거리","석촌","석촌고분","선릉","선바위","선유도","선정릉","성균관대","성수","성신여대입구","성환","세류","세마","소요산","송정","송탄","송파","송파나루","수락산","수리산","수서","수원","수유","수진","숙대입구","숭실대입구","시민공원","시청","신금호","신길","신길온천","신내","신논현","신당","신대방","신대방삼거리","신도림","신림","신목동","신반포","신방화","신사","신설동","신용산","신이문","신정","신중동","신창","신촌","신풍","신흥","쌍문","쌍용","아산","아시아드경기장","아차산","아현","안국","안산","안암","안양","암사","압구정","애오개","약수","양재","양주","양천향교","양평","어린이대공원","언주","여의나루","여의도","역삼","역촌","연신내","염창","영등포","영등포구청","영등포시장","오금","오남","오목교","오산","오산대","오이도","옥수","온수","온양온천","올림픽공원","완정","왕길","왕십리","외대앞","용마산","용산","우장산","운연","원당","원흥","월계","월곡","월드컵경기장","을지로3가","을지로4가","을지로입구","응암","의왕","의정부","이대","이촌","이태원","인덕원","인천가좌","인천대공원","인천시청","일원","잠실","잠실나루","잠실새내","잠원","장승배기","장암","장지","장한평","정발산","정부과천청사","정왕","제기동","종각","종로3가","종로5가","종합운동장","주안","주안국가산단","주엽","중계","중곡","중앙","중앙보훈병원","중화","증미","증산","지축","지행","직산","진위","진접","창동","창동","창신","천안","천왕","천호","철산","청구","청담","청량리","초지","총신대입구(이수)","춘의","충무로","충정로","탕정","태릉입구","평촌","평택","평택지제","하계","하남검단산","하남시청","하남풍산","학동","학여울","한강진","한대앞","한성대입구","한성백제","한양대","합정","행당","혜화","홍대입구","홍제","화곡","화랑대","화서","화정","회기","회룡","회현","효창공원앞","흑석"
    };
    private static String curStation;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> historyList;
    ArrayList<String> bookmarkList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_search);
        searchView = findViewById(R.id.Subway_Search);
        listView_search = findViewById(R.id.Subway_Search_List);
        listView_history = findViewById(R.id.Subway_History_List);
        listView_bookmark = findViewById(R.id.Subway_Bookmark_List);
        listView_search.setVisibility(View.GONE);
        listView_bookmark.setVisibility(View.VISIBLE);
        listView_history.setVisibility(View.VISIBLE);

        db = new DatabaseHelper(this);

        historyList = new ArrayList<>();
        bookmarkList = new ArrayList<>();

        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Subway_Search_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        BookmarkviewData();
        viewData();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subwayList);
        listView_search.setAdapter(arrayAdapter);

        listView_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                db.insertData(name);
                historyList.clear();
                viewData();
                curStation = name;
                Intent intent = new Intent(Subway_Search.this,Subway_Detailed_View.class);
                intent.putExtra("station",curStation);
                startActivity(intent);
                //Toast.makeText(Subway_Search.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               Subway_Search.this.arrayAdapter.getFilter().filter(query);
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Subway_Search.this.arrayAdapter.getFilter().filter(newText);
                if (newText.length() >= 1) {
                    listView_search.setVisibility(View.VISIBLE);
                    listView_bookmark.setVisibility(View.GONE);
                    listView_history.setVisibility(View.GONE);
                }
                else if (newText.length() == 0) {
                    listView_search.setVisibility(View.GONE);
                    listView_bookmark.setVisibility(View.VISIBLE);
                    listView_history.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.subway_search_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //메뉴 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        int curId = item.getItemId();
        switch (curId){
            case R.id.bookmark_all_delete: //북마크 삭제
                All_Delete();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class HistoryListAdapter extends ArrayAdapter<String> {
        private int layout;
        private HistoryListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.history_textView);
                viewHolder.button = (ImageButton) convertView.findViewById(R.id.history_button);
                viewHolder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "북마크 버튼 눌림", Toast.LENGTH_LONG).show();
                        String name = viewHolder.title.getText().toString();
                        curStation = name;
                        Intent intent = new Intent(Subway_Search.this,Subway_Detailed_View.class);
                        intent.putExtra("station",curStation);
                        startActivity(intent);
                        finish();
                    }
                });
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "북마크 버튼 눌림", Toast.LENGTH_LONG).show();
                        String name = viewHolder.title.getText().toString();
                        Insert_Bookmark(name);
                    }
                });
                convertView.setTag(viewHolder);
            }
            else{
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.title.setText(getItem(position));
            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.title.setText(getItem(position));
            return convertView;
        }
    }

    private class BookmarkListAdapter extends ArrayAdapter<String> {
        private int layout;
        private BookmarkListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.bookmark_textView);
                viewHolder.button = (ImageButton) convertView.findViewById(R.id.bookmark_button);
                viewHolder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "북마크 버튼 눌림", Toast.LENGTH_LONG).show();
                        String name = viewHolder.title.getText().toString();
                        curStation = name;
                        Intent intent = new Intent(Subway_Search.this,Subway_Detailed_View.class);
                        intent.putExtra("station",curStation);
                        startActivity(intent);
                        finish();
                    }
                });
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "북마크 버튼 눌림", Toast.LENGTH_LONG).show();
                        String name = viewHolder.title.getText().toString();
                        Delete_Bookmark(name);
                    }
                });
                convertView.setTag(viewHolder);
            }
            else{
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.title.setText(getItem(position));
            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.title.setText(getItem(position));
            return convertView;
        }
    }

    public class ViewHolder {
        TextView title;
        ImageButton button;
    }

    private void Insert_Bookmark(String name){
        db.insertBookmark(name);
        bookmarkList.clear();
        db.deleteData(name);
        historyList.clear();

        BookmarkviewData();
        viewData();
    }

    private void Delete_Bookmark(String name){
        db.deleteBookmark(name);
        bookmarkList.clear();
        db.insertData(name);
        historyList.clear();

        BookmarkviewData();
        viewData();
    }

    private void viewData() {
        Cursor cursor = db.viewData();

        while (cursor.moveToNext()) {
            historyList.add(cursor.getString(1));
        }

        listView_history.setAdapter(new HistoryListAdapter(this, R.layout.history_list_item,historyList));
        //setListViewHeightBasedOnChildren(listView_history);
    }

    private void BookmarkviewData() {
        Cursor cursor = db.BookmarkviewData();

        while (cursor.moveToNext()) {
            bookmarkList.add(cursor.getString(1));
        }

        listView_bookmark.setAdapter(new BookmarkListAdapter(this, R.layout.bookmark_list_item,bookmarkList));
        //setListViewHeightBasedOnChildren(listView_bookmark);
    }

    private void All_Delete(){
        db.alldelete();
        bookmarkList.clear();
        historyList.clear();
        
        BookmarkviewData();
        viewData();
    }

    /*리스트 크기만큼 출력
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }
     */
}
