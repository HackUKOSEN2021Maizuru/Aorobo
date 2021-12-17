package com.example.aorobo.db;

import android.content.Context;

import androidx.room.Room;

public class FavorabilityDataBaseSingleton {
    private static FavorabilityDataBase instance = null;

    public static FavorabilityDataBase getInstance(Context context) {
        if (instance != null) {
            return instance;

        }

        instance = Room.databaseBuilder(context,
                FavorabilityDataBase.class, "favorability_database").build();
        return instance;
    }
}
