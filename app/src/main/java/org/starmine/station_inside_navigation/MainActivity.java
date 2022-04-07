package org.starmine.station_inside_navigation;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Main_Route_Btn = (Button) findViewById(R.id.Main_Route_Btn);
        Main_Route_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Map.class);
                startActivity(intent);
            }
        });

        /* 내부안내 인텐트
        Button Main_Guide_Btn = (Button) findViewById(R.id.Main_Guide_Btn);
        Main_Guide_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subway_Map.class);
                startActivity(intent);
            }
        });
        */
    }
}