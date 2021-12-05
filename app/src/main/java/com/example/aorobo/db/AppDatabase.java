package com.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.aorobo.db.Log.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract com.example.aorobo.db.LogDao LogDao();
}
