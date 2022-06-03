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
    Fragment_Transfer_One fragment_transfer1;
    Fragment_Transfer_Two fragment_transfer2;

    static int Fragment_sub = 0;

    String Time;
    String[] route;
    int[] route_line;
    int transfer_Count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.route,container,false);

        fragment_transfer1 = new Fragment_Transfer_One();
        fragment_transfer2 = new Fragment_Transfer_Two();

        route_time = viewGroup.findViewById(R.id.Route_Time_Text);
        //subway_route = viewGroup.findViewById(R.id.Route_Text);

        setRoute();

        return viewGroup;
    }
    public void setRoute(){
        transfer_Count = 0;

        Bundle bundle= getArguments();
        Time = String.valueOf(bundle.getInt("time"));

        route_time.setText(Time+"분");


        route = bundle.getStringArray("subway_route");
        route_line = bundle.getIntArray("subway_route_line");


        String route11 = "";

        for(int i=0;i<route.length-1;i++){
            if (route[i]!=null){
                if(route[i].equals(route[i + 1])){
                    route11 = route11 + route[i] + route_line[i]+ "환승->" + route[i+1] + route_line[i+1] + "i값"+i+"\n";
                    i = i+1;
                    transfer_Count++;
                }else{
                    route11 = route11 + route[i] + route_line[i] + "i값"+i+"\n";
                }
            }
        }

        System.out.println("경로수정"+route11);

        //System.out.println("환승 횟수: "+transfer_Count);


        if(transfer_Count==1){
            Bundle route_bundle = new Bundle();
            route_bundle.putStringArray("subway_route", route);
            route_bundle.putIntArray("subway_route_line", route_line);
            fragment_transfer1.setArguments(route_bundle);

            if (Fragment_sub==1){
                fragment_transfer1.setRouteTransfer();
            }else{
                getChildFragmentManager().beginTransaction().replace(R.id.Transfer_Container,fragment_transfer1).commit();
                Fragment_sub=1;
            }

        }else if(transfer_Count==2){
            Bundle route_bundle = new Bundle();
            route_bundle.putStringArray("subway_route", route);
            route_bundle.putIntArray("subway_route_line", route_line);
            fragment_transfer2.setArguments(route_bundle);

            if(Fragment_sub==2){
                fragment_transfer2.setRouteTransfer();
            }else{
                getChildFragmentManager().beginTransaction().replace(R.id.Transfer_Container,fragment_transfer2).commit();
                Fragment_sub=2;
            }

        }
    }
}
