package michael.vdw.bxlartwalk.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import michael.vdw.bxlartwalk.Models.CbArt;

@Dao
public interface CbArtDao {
    @Insert
    void insertCbArt(CbArt cbArt);

    @Update
    void updateCbArt(CbArt cbArt);

    @Query("SELECT * FROM CbArt")
    List<CbArt> getAllCb();
}
