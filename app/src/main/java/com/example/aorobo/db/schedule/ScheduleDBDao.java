package com.example.aorobo.db.schedule;

import androidx.lifecycle.LiveData;
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

    @Query("delete from scheduledb where id = :id")
    void delete(int id);

    @Query("DELETE FROM scheduledb")
    public void deleteAll();

    @Query("SELECT * FROM scheduledb ORDER BY start")
    LiveData<List<ScheduleDB>> sort();

}
