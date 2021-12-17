package colob.example.aorobo.db.favorability;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Favorability {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "favorability")
    public long favorability;

    @ColumnInfo(name = "studydate")
    public Date date;

    public Favorability(long favorability,Date date) {
        this.favorability = favorability;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setfavorability(long favorability) {
        this.favorability = favorability;
    }

    public long getfavorability() {
        return favorability;
    }
    public Date getdate() {
        return date;
    }
}
