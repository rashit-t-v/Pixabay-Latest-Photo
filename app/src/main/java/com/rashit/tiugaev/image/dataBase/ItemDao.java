package com.rashit.tiugaev.image.dataBase;

import com.rashit.tiugaev.image.dataBase.DataBase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@androidx.room.Dao
public interface ItemDao {

    @Query("SELECT * FROM `favorits`")
     LiveData<List<DataBase>> getAllItems();

    @Insert
    void insertItem(DataBase dataBase);

    @Delete
    void deletetItem(DataBase dataBase);

    @Query("DELETE FROM `favorits` WHERE id = :userId")
    int deleteByUserId(int userId);

    @Query("SELECT * FROM `favorits` WHERE id = :userId")
    boolean searchByUserId(int userId);

}
