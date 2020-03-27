package michael.vdw.bxlartwalk.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate toDate(String dateString) {
        return (dateString == null) ? null : LocalDate.parse(dateString);
    }

    @TypeConverter
    public static String toDateString(LocalDate localDate) {
        return (localDate == null) ? null : localDate.toString();
    }
}
