package com.lennydennis.mvvmarchitecture;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lennydennis.mvvmarchitecture.Model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao mNoteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance; 
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabase(instance).execute();
        }
    };



    private static class PopulateDatabase extends AsyncTask<Void,Void,Void>{
        private NoteDao mNoteDao;

        public PopulateDatabase(NoteDatabase noteDatabase) {
            mNoteDao = noteDatabase.mNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.insert(new Note("Title 1", "Descriptioin 1", 1));
            mNoteDao.insert(new Note("Title 2", "Descriptioin 2", 2));
            mNoteDao.insert(new Note("Title 3", "Descriptioin 3", 3));
            mNoteDao.insert(new Note("Title 4", "Descriptioin 4 ", 4));
            return null;
        }
    }
}
