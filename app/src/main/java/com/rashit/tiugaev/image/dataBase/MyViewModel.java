package com.rashit.tiugaev.image.dataBase;
import android.app.Application;
import android.os.AsyncTask;
import com.rashit.tiugaev.image.dataBase.DataBase;
import com.rashit.tiugaev.image.dataBase.VersionDatabase;
import com.rashit.tiugaev.image.activity.DetailCallBack;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class MyViewModel extends AndroidViewModel {

    private static VersionDatabase database;
    private LiveData<List<DataBase>> items;

    public MyViewModel(@NonNull Application application) {
        super(application);
        database = VersionDatabase.getInstance(getApplication());
        items = database.mDao().getAllNotes();
    }

    public LiveData<List<DataBase>> getItems() {
        return items;
    }

    public void insetItem(DataBase insertItemDataBase) {
        new InsertDataBaseTask().execute(insertItemDataBase);
    }

    private static class InsertDataBaseTask extends AsyncTask<DataBase, Void, Void> {
        @Override
        protected Void doInBackground(DataBase... dataBase) {
            if (dataBase != null && dataBase.length > 0) {
                database.mDao().insertItem(dataBase[0]);
            }
            return null;
        }
    }
    public void deleteItem(DataBase deleteItemDataBase) {
        new DeleteDataBaseTask().execute(deleteItemDataBase);
    }

    private static class DeleteDataBaseTask extends AsyncTask<DataBase, Void, Void> {
        @Override
        protected Void doInBackground(DataBase... dataBases) {
            if (dataBases != null && dataBases.length > 0) {
                database.mDao().deletetItem(dataBases[0]);
            }
            return null;
        }
    }
    public void deleteItemById(Integer integer) {
        new DeleteItemIdDataBaseTask().execute(integer);
    }
    private static class DeleteItemIdDataBaseTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... dataBases) {
            if (dataBases != null && dataBases.length > 0) {
                database.mDao().deleteByUserId(dataBases[0]);
            }
            return null;
        }
    }
    public void searchItemById(Integer integer, DetailCallBack photo) {
        new SearchItemIdDataBaseTaskk(photo).execute(integer);
    }
    private class  SearchItemIdDataBaseTaskk extends AsyncTask<Integer, Void, Boolean> {
        //interface
        public DetailCallBack photo = null;
        public SearchItemIdDataBaseTaskk(DetailCallBack photo) {
            this.photo = photo;
        }
        @Override
        protected Boolean doInBackground(Integer... dataBases) {
            if (dataBases != null && dataBases.length > 0) {
                if (database.mDao().searchByUserId(dataBases[0])) {
                    return true;
                }
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            photo.onSearchComliteByUserId(aBoolean);
        }
    }

}
