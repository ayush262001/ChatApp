package com.example.createanote;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "time")
    private String time;

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getText(){return text;}
    public void setText(String text){this.text = text;}

    public String getTime(){return time;}
    public void setTime(String time){this.time = time;}



}
