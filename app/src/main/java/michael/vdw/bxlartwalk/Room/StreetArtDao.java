package michael.vdw.bxlartwalk.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import michael.vdw.bxlartwalk.Models.StreetArt;

@Dao
public interface StreetArtDao {

    @Insert
    void insertStreetArt(StreetArt streetArt);

    @Update
    void updateStreetArt(StreetArt streetArt);

    @Query("select * from StreetArt")
    List<StreetArt> getAllStreetArt();

    @Query("delete from StreetArt")
    void nukeTable();

    @Query("select*from StreetArt where id like:id")
    StreetArt findById(String id);
}
