package michael.vdw.bxlartwalk.Room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.Utils.Converters;

@Database(version = 8, entities = {CbArt.class, StreetArt.class}, exportSchema = false)
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
        return Room.databaseBuilder(context, CbArtDataBase.class, "cbart.db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public abstract CbArtDao cbArtDao();

    public abstract StreetArtDao streetArtDao();

    public static ExecutorService dbExecutor = Executors.newFixedThreadPool(4);

}
