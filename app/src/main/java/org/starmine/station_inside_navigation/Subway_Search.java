package org.starmine.station_inside_navigation;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Subway_Search extends AppCompatActivity {
    SearchView searchView;
    ListView listView_search, listView_history, listView_bookmark;
    DatabaseHelper db;
    String[] subwayList = {"사당역" , "총신대입구역", "남태령역"};



    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> historyList;
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

        db = new DatabaseHelper(this);

        historyList = new ArrayList<>();
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
                Toast.makeText(Subway_Search.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
            }
        });

        viewData();

        listView_history.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Subway_Search.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();;
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
                    listView_history.setVisibility(View.VISIBLE);

                }
                return true;
            }
        });

    }

    private void viewData() {
        Cursor cursor = db.viewData();

        while (cursor.moveToNext()) {
            historyList.add(cursor.getString(1));
        }

        adapter = new ArrayAdapter<String>(Subway_Search.this, android.R.layout.simple_list_item_1, historyList);
        listView_history.setAdapter(adapter);
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
}
