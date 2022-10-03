package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.starmine.station_inside_navigation.Inside_Navigation_test;
import org.starmine.station_inside_navigation.R;

public class Beomgyeb2 extends Fragment {
    ViewGroup viewGroup;
    Button B1,B2,B3,B4,B5,B6,B7,B8,B9,B10;
    ImageView goStairs;

    Button CS;
    Button route1,route2,route3,route4;

    int stationnum;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.beomgye_b2, container, false);

        CS = viewGroup.findViewById(R.id.currentS);
        route1=viewGroup.findViewById(R.id.route1);
        route2=viewGroup.findViewById(R.id.route2);
        route3=viewGroup.findViewById(R.id.route3);
        route4=viewGroup.findViewById(R.id.route4);;

        CS.setVisibility(View.INVISIBLE);
        route1.setVisibility(View.INVISIBLE);
        route2.setVisibility(View.INVISIBLE);
        route3.setVisibility(View.INVISIBLE);
        route4.setVisibility(View.INVISIBLE);

        B1=viewGroup.findViewById(R.id.S1);
        B2=viewGroup.findViewById(R.id.S2);
        B3=viewGroup.findViewById(R.id.S3);
        B4=viewGroup.findViewById(R.id.S4);
        B5=viewGroup.findViewById(R.id.S5);
        B6=viewGroup.findViewById(R.id.S6);
        B7=viewGroup.findViewById(R.id.S7);
        B8=viewGroup.findViewById(R.id.S8);
        B9=viewGroup.findViewById(R.id.S9);
        B10=viewGroup.findViewById(R.id.S10);



        Bundle curstation = getArguments();
        if(curstation != null){
            stationnum = curstation.getInt("stationnum");
        }

        if (stationnum==1){
            setstairColor();
        }



        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("1");
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("2");
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("3");
            }
        });

        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("4");
            }
        });

        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("5");
            }
        });

        B6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("6");
            }
        });

        B7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("7");
            }
        });

        B8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("8");
            }
        });

        B9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("9");
            }
        });

        B10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartText("10");
            }
        });

        return viewGroup;
    }

    public void setstairColor(){

        CS.setVisibility(View.VISIBLE);
        route1.setVisibility(View.VISIBLE);
        route2.setVisibility(View.VISIBLE);
        route3.setVisibility(View.VISIBLE);
        route4.setVisibility(View.VISIBLE);


        goStairs = viewGroup.findViewById(R.id.gostairs);
        goStairs.setBackgroundResource(R.drawable.red_border);
    }
}
