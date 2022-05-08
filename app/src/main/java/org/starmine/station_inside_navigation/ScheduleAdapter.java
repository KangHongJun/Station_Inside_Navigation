package org.starmine.station_inside_navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    ArrayList<Schedule> items = new ArrayList<Schedule>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.test_item,viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Schedule item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Schedule item){
        items.add(item);
    }

    public void setItems(ArrayList<Schedule> items){
        this.items = items;
    }

    public Schedule getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Schedule item){
        items.set(position,item);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView UP_textView;
        TextView DOWN_textView;

        public ViewHolder(View itemView) {
            super(itemView);

            UP_textView = itemView.findViewById(R.id.testUP);
            DOWN_textView = itemView.findViewById(R.id.testDOWN);
        }
        public void setItem(Schedule item){
            UP_textView.setText(item.getUP());
            DOWN_textView.setText(item.getDOWN());
        }
    }




}
