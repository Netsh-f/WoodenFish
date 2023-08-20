package com.example.woodenfish2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NumberDao {
    @Insert
    void insertNumber(NumberEntity number);

    @Query("SELECT * FROM numbers ORDER BY id DESC LIMIT 1")
    NumberEntity getLastNumberEntity();

    @Query("SELECT * FROM numbers ORDER BY id DESC LIMIT 1")
    LiveData<NumberEntity> getLastLiveDataNumber();

    @Update
    void updateNumber(NumberEntity number);
}
