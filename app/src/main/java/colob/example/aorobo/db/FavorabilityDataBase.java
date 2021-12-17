package colob.example.aorobo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import colob.example.aorobo.db.favorability.Favorability;
import colob.example.aorobo.db.favorability.FavorabilityDao;

@Database(entities = {Favorability.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FavorabilityDataBase extends RoomDatabase {
    public abstract FavorabilityDao FavorabilityDao();
}
