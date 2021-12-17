package colob.example.aorobo.db;

import android.content.Context;

import androidx.room.Room;

public class TimeDatabaseSingleton {
    private static TimeDatabase instance = null;

    public static TimeDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;

        }

        instance = Room.databaseBuilder(context,
                TimeDatabase.class, "time_database").build();
        return instance;
    }
}
