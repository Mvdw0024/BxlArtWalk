package michael.vdw.bxlartwalk.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Collection;

@Entity // ROOM preparation
public class CbArt implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String id;
    public String characters, authors, photourl;
//    public LatLng geocoordinates;
    public Double lat, lng;
    public int year;

    @Ignore
    public CbArt() {
    }

    public CbArt(String id, String characters, String authors, String photourl, Double lat, Double lng, int year) {
        this.id = id;
        this.characters = characters;
        this.authors = authors;
        this.photourl = photourl;
//        this.geocoordinates = geocoordinates;
        this.lat = lat;
        this.lng = lng;
        this.year = year;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

//    public LatLng getGeocoordinates() {
//        return geocoordinates;
//    }
//
//    public void setGeocoordinates(LatLng geocoordinates) {
//        this.geocoordinates = geocoordinates;
//    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    // nog geen idee, of dit nodig zal zijn, maar just in case
//    @Override
//    public String toString() {
//        return "CbArt{" +
//                "characters='" + characters + '\'' +
//                ", authors='" + authors + '\'' +
//                ", photourl='" + photourl + '\'' +
//                ", geocoordinates=" + geocoordinates +
//                ", year=" + year +
//                '}';
//    }
}
