package michael.vdw.bxlartwalk.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity
public class StreetArt implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String id;
    public String artists, workname, adres, verduidelijking, photoid;
    public int jaar, isFavorite;
    public Double lat, lng;

    @Ignore
    public StreetArt() {
    }

    public StreetArt(@NonNull String id, String artists, String workname, String adres, String verduidelijking, String photoid,  int jaar, Double lat, Double lng) {
        this.id = id;
        this.artists = artists;
        this.workname = workname;
        this.adres = adres;
        this.verduidelijking = verduidelijking;
        this.photoid = photoid;
        this.jaar = jaar;
        this.lat = lat;
        this.lng = lng;
        this.isFavorite = 0;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getArtists() {
        return artists;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getVerduidelijking() {
        return verduidelijking;
    }

    public void setVerduidelijking(String verduidelijking) {
        this.verduidelijking = verduidelijking;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
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
}
