package org.starmine.station_inside_navigation;

//시간표 - 리사이클 뷰에 들어갈 변수 저장
public class Schedule {
    String UP_Schedule;
    String DOWN_Schedule;

    public Schedule (String UP_Schedule, String DOWN_Schedule) {
        this.UP_Schedule = UP_Schedule;
        this.DOWN_Schedule = DOWN_Schedule;
    }

    public String getUP(){
        return UP_Schedule;
    }

    public void setUP(String UP_Schedule){
        this.UP_Schedule = UP_Schedule;
    }

    public String getDOWN(){
        return DOWN_Schedule;
    }

    public void setDOWN(String DOWN_Schedule){
        this.DOWN_Schedule = DOWN_Schedule;
    }
}
