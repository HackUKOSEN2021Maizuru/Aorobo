package com.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {com.example.aorobo.db.time.TimeDB.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class StudyTimeDataBase extends RoomDatabase {
    public abstract com.example.aorobo.db.time.TimeDBDao TimeDBDao();
}
