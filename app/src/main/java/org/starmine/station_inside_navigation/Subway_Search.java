package org.starmine.station_inside_navigation;

import android.content.Context;
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
    String[] subwayList = {"사당역" , "총신대입구역", "남태령역"};

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

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subwayList);
        listView_search.setAdapter(arrayAdapter);

        listView_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                db.insertData(name);
                historyList.clear();
                viewData();
                //Toast.makeText(Subway_Search.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        BookmarkviewData();
        viewData();

        listView_history.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(Subway_Search.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();;
                finish();
            }
        }));

        listView_bookmark.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(Subway_Search.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();;
                finish();
            }
        }));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               Subway_Search.this.arrayAdapter.getFilter().filter(query);
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Subway_Search.this.arrayAdapter.getFilter().filter(newText);
                adapter = new ArrayAdapter<String>(Subway_Search.this, android.R.layout.simple_list_item_1, historyList);
                listView_history.setAdapter(adapter);
                if (newText.length() >= 1) {
                    listView_search.setVisibility(View.VISIBLE);
                    listView_bookmark.setVisibility(View.GONE);
                    listView_history.setVisibility(View.GONE);
                }
                else if (newText.length() == 0) {
                    listView_search.setVisibility(View.GONE);
                    listView_bookmark.setVisibility(View.VISIBLE);

                    if(db.getTableRowCount() == 0){
                        listView_history.setVisibility(View.GONE);
                    }
                    else
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
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "북마크 버튼 눌림", Toast.LENGTH_LONG).show();
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
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "북마크 버튼 눌림", Toast.LENGTH_LONG).show();
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
