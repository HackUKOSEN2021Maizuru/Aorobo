package com.example.aorobo.db;

import android.content.Context;

import androidx.room.Room;

public class StudyTimeDataBaseSingleton {
    private static StudyTimeDataBase instance = null;

    public static StudyTimeDataBase getInstance(Context context) {
        if (instance != null) {
            return instance;

        }

        instance = Room.databaseBuilder(context,
                StudyTimeDataBase.class, "study_time_database").build();
        return instance;
    }
}