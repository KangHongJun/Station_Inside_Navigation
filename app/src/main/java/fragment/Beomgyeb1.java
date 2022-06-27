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

public class Beomgyeb1 extends Fragment {
    ViewGroup viewGroup;
    Button A8,A7,A6,A5,A4,A4_1,A3,A2,A1;
    ImageView b1s1;

    int stationnum;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.beomgye_b1, container, false);

        A8 = viewGroup.findViewById(R.id.Arrival8);
        A7 = viewGroup.findViewById(R.id.Arrival7);
        A6 = viewGroup.findViewById(R.id.Arrival6);
        A5 = viewGroup.findViewById(R.id.Arrival5);
        A4 = viewGroup.findViewById(R.id.Arrival4);
        A4_1 = viewGroup.findViewById(R.id.Arrival4_1);
        A3 = viewGroup.findViewById(R.id.Arrival3);
        A2 = viewGroup.findViewById(R.id.Arrival2);
        A1 = viewGroup.findViewById(R.id.Arrival1);


        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("1");
            }
        });

        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("2");
            }
        });

        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("3");
            }
        });

        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("4");
            }
        });

        A4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("4-1");
            }
        });

        A5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("5");
            }
        });

        A6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("6");
            }
        });

        A7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("7");
            }
        });

        A8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Inside_Navigation_test)getActivity()).setStartSubText("8");
            }
        });

        Bundle curstation = getArguments();
        if(curstation != null){
            stationnum = curstation.getInt("stationnum");
        }

        if (stationnum==1){
            setstairColor2();
        }

        return viewGroup;
    }

    public void setstairColor2(){
        b1s1 = viewGroup.findViewById(R.id.B1S1);
        b1s1.setBackgroundResource(R.drawable.blue_border);
        A2.setBackgroundResource(R.drawable.circle3);
    }
}
