package org.starmine.station_inside_navigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.starmine.station_inside_navigation.R;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    ListView listView_bookmark;
    DatabaseHelper db;
    ArrayList<String> bookmarkList;
    private static String curStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);
        listView_bookmark = findViewById(R.id.Bookmark_List);

        db = new DatabaseHelper(this);
        bookmarkList = new ArrayList<>();

        BookmarkviewData();

        //툴바 세팅
        Toolbar toolbar = findViewById(R.id.Bookmark_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }
    //메뉴적용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookmark_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    //메뉴 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        /*int curId = item.getItemId();
        switch (curId){
            case R.id.bookmark_add: //북마크 추가
                Toast.makeText(this,"즐겨찾기 추가",Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_delete: //북마크 삭제
                Toast.makeText(this,"즐겨찾기 삭제",Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
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
                        Intent intent = new Intent(Bookmark.this,Subway_Detailed_View.class);
                        intent.putExtra("station",curStation);
                        startActivity(intent);
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

    private void Delete_Bookmark(String name){
        db.deleteBookmark(name);
        bookmarkList.clear();
        db.insertData(name);

        BookmarkviewData();
    }

    private void BookmarkviewData() {
        Cursor cursor = db.BookmarkviewData();

        while (cursor.moveToNext()) {
            bookmarkList.add(cursor.getString(1));
        }

        listView_bookmark.setAdapter(new BookmarkListAdapter(this, R.layout.bookmark_list_item,bookmarkList));
        //setListViewHeightBasedOnChildren(listView_bookmark);
    }
}