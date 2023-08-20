package com.example.woodenfish2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NumberEntity.class}, version = 1)
public abstract class NumberDatabase extends RoomDatabase {
    public abstract NumberDao numberDao();
}
