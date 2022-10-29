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

public class Fragment_Transfer_Two extends Fragment {
    ViewGroup viewGroup;
    ImageView Trans_img1, Trans_img2;
    ImageView Trans_stick1, Trans_stick2, Trans_stick3;
    TextView Trans_text1, Trans_text2, Trans_text3, Trans_text4;

    String[] route;
    int[] route_line;

    //환승 횟수 및 환승역 번호
    int transfer_count;
    int[] transfer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.transfer_two,container,false);

        Trans_img1 = viewGroup.findViewById(R.id.TransTwo_img1);
        Trans_img2 = viewGroup.findViewById(R.id.TransTwo_img2);

        Trans_stick1 = viewGroup.findViewById(R.id.TransTwo_stick1);
        Trans_stick2 = viewGroup.findViewById(R.id.TransTwo_stick2);
        Trans_stick3 = viewGroup.findViewById(R.id.TransTwo_stick3);

        Trans_text1 = viewGroup.findViewById(R.id.TransTwo_Text1);
        Trans_text2 = viewGroup.findViewById(R.id.TransTwo_Text2);
        Trans_text3 = viewGroup.findViewById(R.id.TransTwo_Text3);
        Trans_text4 = viewGroup.findViewById(R.id.TransTwo_Text4);

        setRouteTransfer();


        return viewGroup;
    }
    public void setRouteTransfer(){
        transfer_count=0;
        transfer= new int[2];

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
                    System.out.println("i값:"+i+"transfer_count"+transfer_count);
                    transfer[transfer_count] = i;

                    //환승역 두번 탐색하면 중단
                    if (transfer_count==0){
                        Trans_text3.setText(route_trans);
                    }else if(transfer_count==1){
                        Trans_text2.setText(route_trans);
                    }else if (transfer_count==2){
                        break;
                    }
                    transfer_count++;
                }
            }
        }
        switch (route_line[transfer[1]]){
            case 1:
                Trans_stick2.setBackgroundResource(R.color.line1);
                break;
            case 2:
                Trans_stick2.setBackgroundResource(R.color.line2);
                break;
            case 3:
                Trans_stick2.setBackgroundResource(R.color.line3);
                break;
            case 4:
                Trans_stick2.setBackgroundResource(R.color.line4);
                break;
            case 5:
                Trans_stick2.setBackgroundResource(R.color.line5);
                break;
            case 6:
                Trans_stick2.setBackgroundResource(R.color.line6);
                break;
            case 7:
                Trans_stick2.setBackgroundResource(R.color.line7);
                break;
            case 8:
                Trans_stick2.setBackgroundResource(R.color.line8);
                break;
            case 9:
                Trans_stick2.setBackgroundResource(R.color.line9);
                break;
            default:
                break;
        }

        switch (route_line[transfer[0]]){
            case 1:
                Trans_stick3.setBackgroundResource(R.color.line1);
                Trans_img2.setImageResource(R.drawable.img_line1);
                break;
            case 2:
                Trans_stick3.setBackgroundResource(R.color.line2);
                Trans_img2.setImageResource(R.drawable.img_line2);
                break;
            case 3:
                Trans_stick3.setBackgroundResource(R.color.line3);
                Trans_img2.setImageResource(R.drawable.img_line3);
                break;
            case 4:
                Trans_stick3.setBackgroundResource(R.color.line4);
                Trans_img2.setImageResource(R.drawable.img_line4);
                break;
            case 5:
                Trans_stick3.setBackgroundResource(R.color.line5);
                Trans_img2.setImageResource(R.drawable.img_line5);
                break;
            case 6:
                Trans_stick3.setBackgroundResource(R.color.line6);
                Trans_img2.setImageResource(R.drawable.img_line6);
                break;
            case 7:
                Trans_stick3.setBackgroundResource(R.color.line7);
                Trans_img2.setImageResource(R.drawable.img_line7);
                break;
            case 8:
                Trans_stick3.setBackgroundResource(R.color.line8);
                Trans_img2.setImageResource(R.drawable.img_line8);
                break;
            case 9:
                Trans_stick3.setBackgroundResource(R.color.line9);
                Trans_img2.setImageResource(R.drawable.img_line9);
                break;
            default:
                break;
        }

        System.out.println("transfer_count"+transfer[transfer_count-1]);


        Trans_text1.setText(route[0]);
        Trans_text4.setText(route[transfer[transfer_count-1]+3]);

    }

}
