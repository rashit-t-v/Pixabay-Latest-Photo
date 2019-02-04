package com.rashit.tiugaev.image.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.NotesDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class MyViewModel extends AndroidViewModel {

    private static NotesDatabase database;
    private LiveData<List<DataBase>> notes;

    public MyViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
        notes = database.notesDao().getAllNotes();
    }

    public LiveData<List<DataBase>> getNotes() {
        return notes;
    }

    public void insetNote(DataBase insertNotesDataBase) {
        new InsertDataBaseTask().execute(insertNotesDataBase);
    }

    private static class InsertDataBaseTask extends AsyncTask<DataBase, Void, Void> {

        @Override
        protected Void doInBackground(DataBase... dataBasesNote) {
            if (dataBasesNote != null && dataBasesNote.length > 0) {
                database.notesDao().insetNote(dataBasesNote[0]);
            }
            return null;
        }
    }

    public void deleteNote(DataBase deleteNotesDataBase) {
        new DeleteDataBaseTask().execute(deleteNotesDataBase);
    }

    private static class DeleteDataBaseTask extends AsyncTask<DataBase, Void, Void> {

        @Override
        protected Void doInBackground(DataBase... dataBasesNote) {
            if (dataBasesNote != null && dataBasesNote.length > 0) {
                database.notesDao().deletetNote(dataBasesNote[0]);
            }

            return null;
        }
    }
}
