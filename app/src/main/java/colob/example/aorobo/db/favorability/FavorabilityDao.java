package colob.example.aorobo.db.favorability;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface  FavorabilityDao {
    @Query("SELECT * FROM favorability")
    List<Favorability> getAll();

    @Query("SELECT * FROM favorability WHERE id IN (:ids)")
    List<Favorability> loadAllByIds(int[] ids);

    @Insert
    void insertAll(Favorability... time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorability time);

    @Delete
    void delete(Favorability time);

    @Query("DELETE FROM favorability")
    public void deleteAll();
}
