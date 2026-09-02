package com.example.fitnessapp.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Calories_table")
public class Calories {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public float calories;
    public long date;


    public Calories(float calories, long date){
        this.calories = calories;
        this.date = date;
    }
}
