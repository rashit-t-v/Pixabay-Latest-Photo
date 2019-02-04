package com.rashit.tiugaev.image.interfaces;

import com.rashit.tiugaev.image.dataBase.DataBase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM `favorits`")
     LiveData<List<DataBase>> getAllNotes();

    @Insert
    void insetNote(DataBase dataBase);

    @Delete
    void deletetNote(DataBase dataBase);
}
