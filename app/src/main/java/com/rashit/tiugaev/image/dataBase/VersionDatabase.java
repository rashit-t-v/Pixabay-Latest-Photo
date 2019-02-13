package com.rashit.tiugaev.image.dataBase;

import android.content.Context;

import com.rashit.tiugaev.image.interfaces.ItemDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {DataBase.class},version = 1,exportSchema = false)
public abstract class VersionDatabase extends RoomDatabase {

    private static VersionDatabase dataBase;
    private static final String DB_NAME = "favorite.db";
    private static final Object LOCK = new Object();

    public static VersionDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, VersionDatabase.class, DB_NAME)
                        .build();
            }
        }
        return dataBase;
    }
    public abstract ItemDao mDao();
}
