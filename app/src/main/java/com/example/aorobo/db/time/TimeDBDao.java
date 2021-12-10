package com.example.aorobo.db.time;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimeDBDao {


    @Query("SELECT * FROM timedb")
    List<TimeDB> getAll();

    @Query("SELECT * FROM timedb WHERE id IN (:ids)")
    List<TimeDB> loadAllByIds(int[] ids);

    @Insert
    void insertAll(TimeDB... time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeDB time);

    @Delete
    void delete(TimeDB time);

    @Query("DELETE FROM timedb")
    public void deleteAll();


}
