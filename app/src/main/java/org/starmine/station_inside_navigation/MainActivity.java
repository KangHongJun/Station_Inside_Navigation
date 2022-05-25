package org.starmine.station_inside_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Graph g = new Graph(13);//
//        g.input(1, 2, 9);
//        g.input(1, 4, 8);
//        g.input(1, 7, 6);
//
//        g.input(2, 1, 9);
//        g.input(2, 3, 8);
//
//        g.input(3, 2, 8);
//        g.input(3, 9, 7);
//
//        g.input(4, 1, 8);
//        g.input(4, 5, 10);
//
//
//        g.input(5, 4, 10);
//        g.input(5, 6, 7);
//        g.input(5, 10, 10);
//
//        g.input(6, 5, 7);
//        g.input(6, 7, 8);
//
//        g.input(7, 1, 6);
//        g.input(7, 6, 8);
//        g.input(7, 8, 6);
//        g.input(7, 13, 7);
//
//        g.input(8, 1, 10);
//        g.input(8, 7, 6);
//        g.input(8, 9, 10);
//
//        g.input(9, 8, 10);
//        g.input(9, 13, 13);
//
//        g.input(10, 5, 11);
//        g.input(10, 11, 9);
//
//        g.input(11, 10, 9);
//        g.input(11, 12, 7);
//
//        g.input(12, 11, 7);
//        g.input(12, 13, 7);
//
//        g.input(13, 7, 7);
//        g.input(13, 9, 13);
//        g.input(13, 12, 7);
//
//        g.dijkstra(1);

        Graph g = new Graph(8);
        g.input(6, 2, 3);
        g.input(6, 5, 4);
        g.input(6, 4, 4);
        g.input(2, 3, 2);
        g.input(3, 4, 1);
        g.input(4, 5, 2);
        g.input(5, 1, 4);
        g.input(4, 7, 6);
        g.input(7, 1, 3);
        g.input(3, 8, 3);
        g.input(1, 8, 2);
        g.dijkstra(1);

        Button Main_Route_Btn = (Button) findViewById(R.id.Main_Route_Btn);
        Main_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Map.class);
                startActivity(intent);
            }
        });

        //내부안내 인텐트
        Button Main_Guide_Btn = (Button) findViewById(R.id.Main_Guide_Btn);
        Main_Guide_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Inside_Navigation.class);
                startActivity(intent);
            }
        });
    }
}