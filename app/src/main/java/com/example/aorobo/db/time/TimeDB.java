package com.example.aorobo.db.time;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class TimeDB {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "time")
    public long time;

    public TimeDB(long time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void settime(long time) {
        this.time = time;
    }

    public long gettime() {
        return time;
    }

}
