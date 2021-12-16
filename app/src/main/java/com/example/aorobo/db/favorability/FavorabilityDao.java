package com.example.aorobo.db.favorability;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.aorobo.db.time.TimeDB;

import java.util.List;

@Dao
public interface  FavorabilityDao {
    @Query("SELECT * FROM favorability")
    List<TimeDB> getAll();

    @Query("SELECT * FROM favorability WHERE id IN (:ids)")
    List<TimeDB> loadAllByIds(int[] ids);

    @Insert
    void insertAll(TimeDB... time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeDB time);

    @Delete
    void delete(TimeDB time);

    @Query("DELETE FROM favorability")
    public void deleteAll();
}
