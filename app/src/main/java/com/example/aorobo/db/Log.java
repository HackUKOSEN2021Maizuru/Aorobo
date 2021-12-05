package com.example.aorobo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Log {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "log")
    private String log;

    public Log(String log) {
        this.log = log;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

}
