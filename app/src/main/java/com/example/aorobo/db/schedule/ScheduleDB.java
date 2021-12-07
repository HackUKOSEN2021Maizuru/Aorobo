package com.example.aorobo.db.schedule;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class ScheduleDB {

    @PrimaryKey(autoGenerate = true)
    private int id;
    /*
    @ColumnInfo(name = "log")
    private long time;
     */
    @ColumnInfo(name = "start")
    Date start;
    @ColumnInfo(name = "end")
    Date end;
    @ColumnInfo(name = "name")
    private String name;

    public ScheduleDB(String name,Date start,Date end) {
        this.name=name;
        this.start=start;
        this.end=end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }


    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

}