package michael.vdw.bxlartwalk.Models;

import com.google.android.gms.maps.model.LatLng;

public class Art {
    //TODO: Check if class is complete or needs adjustments

    private String author, info, imageUrl, type;
    private int id, year;
    private LatLng coordinate;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }

    public Art(String author, String info, String imageUrl, String type, int id, int year, LatLng coordinate) {
        this.author = author;
        this.info = info;
        this.imageUrl = imageUrl;
        this.type = type;
        this.id = id;
        this.year = year;
        this.coordinate = coordinate;
    }
}
