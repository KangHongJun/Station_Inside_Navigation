package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.starmine.station_inside_navigation.R;

public class Fragment_Transfer_One extends Fragment {
    ViewGroup viewGroup;
    ImageView Trans_img1, Trans_img2, Trans_img3;
    ImageView Trans_stick1,Trans_stick2;
    TextView Trans_text1, Trans_text2, Trans_text3;

    String[] route;
    int[] route_line;
    int transfer = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.transfer_one,container,false);

        Trans_img1 = viewGroup.findViewById(R.id.TransOne_img1);
        Trans_img2 = viewGroup.findViewById(R.id.TransOne_img2);
        Trans_img3 = viewGroup.findViewById(R.id.TransOne_img3);

        Trans_stick1 = viewGroup.findViewById(R.id.TransOne_stick1);
        Trans_stick2 = viewGroup.findViewById(R.id.TransOne_stick2);

        Trans_text1 = viewGroup.findViewById(R.id.TransOne_Text1);
        Trans_text2 = viewGroup.findViewById(R.id.TransOne_Text2);
        Trans_text3 = viewGroup.findViewById(R.id.TransOne_Text3);

        setRouteTransfer();


        return viewGroup;
    }

    public void setRouteTransfer(){
        transfer=0;

        Bundle bundle= getArguments();
        route = bundle.getStringArray("subway_route");
        route_line = bundle.getIntArray("subway_route_line");

        switch (route_line[0]){
            case 1:
                Trans_img1.setImageResource(R.drawable.img_line1);
                Trans_stick1.setBackgroundResource(R.color.line1);
                break;
            case 2:
                Trans_img1.setImageResource(R.drawable.img_line2);
                Trans_stick1.setBackgroundResource(R.color.line2);
                break;
            case 3:
                Trans_img1.setImageResource(R.drawable.img_line3);
                Trans_stick1.setBackgroundResource(R.color.line3);
                break;
            case 4:
                Trans_img1.setImageResource(R.drawable.img_line4);
                Trans_stick1.setBackgroundResource(R.color.line4);
                break;
            case 5:
                Trans_img1.setImageResource(R.drawable.img_line5);
                Trans_stick1.setBackgroundResource(R.color.line5);
                break;
            case 6:
                Trans_img1.setImageResource(R.drawable.img_line6);
                Trans_stick1.setBackgroundResource(R.color.line6);
                break;
            case 7:
                Trans_img1.setImageResource(R.drawable.img_line7);
                Trans_stick1.setBackgroundResource(R.color.line7);
                break;
            case 8:
                Trans_img1.setImageResource(R.drawable.img_line8);
                Trans_stick1.setBackgroundResource(R.color.line8);
                break;
            case 9:
                Trans_img1.setImageResource(R.drawable.img_line9);
                Trans_stick1.setBackgroundResource(R.color.line9);
                break;
            default:
                break;
        }

        //환승 텍스트
        String route_trans = "";
        for(int i=0;i<route.length;i++){
            if (route[i]!=null){
                if(route[i].equals(route[i + 1])){
                    route_trans = route[i+1] + route_line[i+1]+ " -> " + route[i] + route_line[i] +"\n";
                    transfer = i;
                    break;
                }
            }
        }

        switch (route_line[transfer]){
            case 1:
                Trans_img3.setImageResource(R.drawable.img_line1);
                Trans_stick2.setBackgroundResource(R.color.line1);
                break;
            case 2:
                Trans_img3.setImageResource(R.drawable.img_line2);
                Trans_stick2.setBackgroundResource(R.color.line2);
                break;
            case 3:
                Trans_img3.setImageResource(R.drawable.img_line3);
                Trans_stick2.setBackgroundResource(R.color.line3);
                break;
            case 4:
                Trans_img3.setImageResource(R.drawable.img_line4);
                Trans_stick2.setBackgroundResource(R.color.line4);
                break;
            case 5:
                Trans_img3.setImageResource(R.drawable.img_line5);
                Trans_stick2.setBackgroundResource(R.color.line5);
                break;
            case 6:
                Trans_img3.setImageResource(R.drawable.img_line6);
                Trans_stick2.setBackgroundResource(R.color.line6);
                break;
            case 7:
                Trans_img3.setImageResource(R.drawable.img_line7);
                Trans_stick2.setBackgroundResource(R.color.line7);
                break;
            case 8:
                Trans_img3.setImageResource(R.drawable.img_line8);
                Trans_stick2.setBackgroundResource(R.color.line8);
                break;
            case 9:
                Trans_img3.setImageResource(R.drawable.img_line9);
                Trans_stick2.setBackgroundResource(R.color.line9);
                break;
            default:
                break;
        }

        Trans_text1.setText(route[0]);
        Trans_text2.setText(route_trans);
        Trans_text3.setText(route[transfer+3]);

        System.out.println(route[4]);

    }
}
