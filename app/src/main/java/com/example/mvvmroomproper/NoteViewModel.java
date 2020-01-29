package com.example.mvvmroomproper;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteDB = NoteRoomDatabase.getDatabase(getApplication());
        noteDao = noteDB.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }
    LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }

    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class OperationsAsyncTask extends AsyncTask<Note, Void, Void>{

        NoteDao mAsyncTaskDao;

        OperationsAsyncTask(NoteDao dao){
            this.mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

//        NoteDao mNoteDao;

        public InsertAsyncTask(NoteDao mNoteDao){
            super(mNoteDao);        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask{

//        NoteDao mNoteDao;

        UpdateAsyncTask(NoteDao noteDao){
//            this.mNoteDao = noteDao;
            super(noteDao);
        }
        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask{

//        NoteDao mNoteDao;

        DeleteAsyncTask(NoteDao noteDao){
//            this.mNoteDao = noteDao;
            super(noteDao);
        }
        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }
}

//todo implement on swipe function
