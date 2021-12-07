package com.example.aorobo.db.schedule;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScheduleDBDao {


    @Query("SELECT * FROM scheduledb")
    List<ScheduleDB> getAll();

    @Query("SELECT * FROM scheduledb WHERE id IN (:ids)")
    List<ScheduleDB> loadAllByIds(int[] ids);

    @Insert
    void insertAll(ScheduleDB... schedule);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScheduleDB schedule);

    @Delete
    void delete(ScheduleDB schedule);

    @Query("DELETE FROM scheduledb")
    public void nukeTable();
}
