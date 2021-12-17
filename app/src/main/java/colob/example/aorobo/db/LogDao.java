package colob.example.aorobo.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LogDao {
    @Query("SELECT * FROM log")
    List<Log> getAll();

    @Query("SELECT * FROM log WHERE id IN (:ids)")
    List<Log> loadAllByIds(int[] ids);

    @Insert
    void insertAll(Log... log);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Log log);

    @Delete
    void delete(Log log);
}