package michael.vdw.bxlartwalk.Room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Utils.Converters;

@Database(version = 1, entities = {CbArt.class}, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class CbArtDataBase extends RoomDatabase {

    public static CbArtDataBase sharedInstance;

    public static CbArtDataBase getSharedInstance(Context c) {
        if (sharedInstance == null) {
            sharedInstance = createDB(c);
        }
        return sharedInstance;
    }


    private static CbArtDataBase createDB(final Context context) {
        Log.d("testje", "hello again!");
        Log.d("context", ""+context);
        return Room.databaseBuilder(context, CbArtDataBase.class, "cbart.db").allowMainThreadQueries().build();
    }

    public abstract CbArtDao cbArtDao();
}
