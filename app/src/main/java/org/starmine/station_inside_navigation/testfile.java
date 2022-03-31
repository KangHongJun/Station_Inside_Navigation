package org.starmine.station_inside_navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class testfile extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4;
    Button button1,button2,button3,button4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        textView1 = findViewById(R.id.testText1);
        textView2 = findViewById(R.id.testText2);
        textView3 = findViewById(R.id.testText3);
        textView4 = findViewById(R.id.testText4);

        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);

        button1= findViewById(R.id.testb1);
        button2= findViewById(R.id.testb2);
        button3= findViewById(R.id.testb3);
        button4= findViewById(R.id.testb4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
            }
        });
    }
}