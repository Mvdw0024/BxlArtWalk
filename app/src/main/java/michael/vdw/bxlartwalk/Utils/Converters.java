package michael.vdw.bxlartwalk.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LatLng fromJSON(Double jsontoLatLng) {
        return (jsontoLatLng == null) ? null : new LatLng(jsontoLatLng,jsontoLatLng);
    }
// aangepast om opnieuw te pushen
    @TypeConverter
    public static Double toLatLng(LatLng latLng) {
        return latLng == null ? null : latLng.latitude;

    }
}
