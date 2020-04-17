package michael.vdw.bxlartwalk.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity // ROOM preparation
public class CbArt implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String id;
    public String characters, authors, photourl, photoid;
    public Double lat, lng;
    public int year, isFavorite;

    @Ignore
    public CbArt() {
    }

    public CbArt(@NonNull String id, String characters, String authors, String photourl, String photoid, Double lat, Double lng, int year) {
        this.id = id;
        this.characters = characters;
        this.authors = authors;
        this.photourl = photourl;
        this.photoid = photoid;
        this.lat = lat;
        this.lng = lng;
        this.year = year;
        this.isFavorite = 0;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
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

    @Override
    public String toString() {
        return "CbArt{" +
                "id='" + id + '\'' +
                ", characters='" + characters + '\'' +
                ", authors='" + authors + '\'' +
                ", photourl='" + photourl + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", year=" + year +
                '}';
    }
}
