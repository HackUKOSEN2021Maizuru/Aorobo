package colob.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import colob.example.aorobo.db.schedule.ScheduleDB;
import colob.example.aorobo.db.schedule.ScheduleDBDao;


@Database(entities = {ScheduleDB.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ScheduleDataBase extends RoomDatabase {
    public abstract ScheduleDBDao ScheduleDBDao();
}
