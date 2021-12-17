package colob.example.aorobo.db;

import android.content.Context;

import androidx.room.Room;

public class ScheduleDataBaseSingleton {
    private static ScheduleDataBase instance = null;

    public static ScheduleDataBase getInstance(Context context) {
        if (instance != null) {
            return instance;

        }

        instance = Room.databaseBuilder(context,
                ScheduleDataBase.class, "schedule_database").build();
        return instance;
    }
}
