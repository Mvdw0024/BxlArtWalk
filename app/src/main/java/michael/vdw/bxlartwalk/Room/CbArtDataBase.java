package michael.vdw.bxlartwalk.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import michael.vdw.bxlartwalk.Models.CbArt;

@Database(version = 1, entities = {CbArt.class}, exportSchema = false)
public abstract class CbArtDataBase extends RoomDatabase {

    public static CbArtDataBase sharedInstance;

    public static CbArtDataBase getSharedInstance(Context c) {
        if (sharedInstance == null) {
            sharedInstance = createDB(c);
        }
        return sharedInstance;
    }


    private static CbArtDataBase createDB(Context c) {
        return Room.databaseBuilder(c, CbArtDataBase.class, "cbart.db").allowMainThreadQueries().build();
    }
    public abstract CbArtDao cbArtDao();
}
