package com.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.aorobo.db.time.TimeDB.class}, version = 2, exportSchema = false)
public abstract class TimeDatabase extends RoomDatabase {
    public abstract com.example.aorobo.db.time.TimeDBDao TimeDBDao();
}
