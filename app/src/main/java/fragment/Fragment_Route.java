package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.starmine.station_inside_navigation.R;

public class Fragment_Route extends Fragment {
    ViewGroup viewGroup;
    TextView route_time;
    TextView subway_route;

    String Time;
    String[] route;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.route,container,false);

        route_time = viewGroup.findViewById(R.id.Route_Time_Text);
        subway_route = viewGroup.findViewById(R.id.Route_Text);

        Bundle bundle= getArguments();
        Time = String.valueOf(bundle.getInt("time"));

        route_time.setText(Time+"분");


        route = bundle.getStringArray("subway_route");

        String route11 = "";

        for(int i=0;i<route.length-1;i++){
            if (route[i]!=null){
                route11 = route11 + route[i] + "\n";
            }
        }

        subway_route.setText(route11);



        return viewGroup;
    }
}
