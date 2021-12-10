package com.example.aorobo.db.time;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class TimeDB {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "time")
    public long time;

    @ColumnInfo(name = "date")
    public Date date;

    public TimeDB(long time,Date date) {
        this.time = time;
        this.date = date;
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
    public Date getdate() {
        return date;
    }

}
