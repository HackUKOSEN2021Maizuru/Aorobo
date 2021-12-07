package com.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {com.example.aorobo.db.schedule.ScheduleDB.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ScheduleDataBase extends RoomDatabase {
    public abstract com.example.aorobo.db.schedule.ScheduleDBDao ScheduleDBDao();
}
