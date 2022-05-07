package org.starmine.station_inside_navigation;

public class Listview_item {
    private String title;
    private int image;

    public Listview_item(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
