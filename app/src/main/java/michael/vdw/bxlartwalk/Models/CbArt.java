package michael.vdw.bxlartwalk.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity // ROOM preparation
public class CbArt implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public int id;
    public String characters, authors, photourl;
    public LatLng geocoordinates;
    public int year;

    @Ignore
    public CbArt() {
    }

    public CbArt(String characters, String authors, String photourl, LatLng geocoordinates, int year) {
        this.characters = characters;
        this.authors = authors;
        this.photourl = photourl;
        this.geocoordinates = geocoordinates;
        this.year = year;
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

    public LatLng getGeocoordinates() {
        return geocoordinates;
    }

    public void setGeocoordinates(LatLng geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // nog geen idee, of dit nodig zal zijn, maar just in case
    @Override
    public String toString() {
        return "CbArt{" +
                "characters='" + characters + '\'' +
                ", authors='" + authors + '\'' +
                ", photourl='" + photourl + '\'' +
                ", geocoordinates=" + geocoordinates +
                ", year=" + year +
                '}';
    }
}