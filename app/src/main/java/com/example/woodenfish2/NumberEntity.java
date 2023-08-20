package com.example.woodenfish2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "numbers")
public class NumberEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int number;
    public NumberEntity(int number){
        this.number = number;
    }
}
