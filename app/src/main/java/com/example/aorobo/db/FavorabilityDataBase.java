package com.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.aorobo.db.favorability.Favorability.class}, version = 1, exportSchema = false)
public abstract class FavorabilityDataBase extends RoomDatabase {
    public abstract com.example.aorobo.db.favorability.FavorabilityDao FavorabilityDao();
}
