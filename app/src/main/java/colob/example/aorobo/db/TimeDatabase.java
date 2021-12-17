package colob.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import colob.example.aorobo.db.time.TimeDB;
import colob.example.aorobo.db.time.TimeDBDao;

@Database(entities = {TimeDB.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TimeDatabase extends RoomDatabase {
    public abstract TimeDBDao TimeDBDao();
}
