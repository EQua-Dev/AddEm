package com.example.mvvmroomproper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDatabase noteRoomInstance;

    static NoteRoomDatabase getDatabase(final Context context) {
        if (noteRoomInstance == null){
            synchronized (NoteRoomDatabase.class) {
                noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteRoomDatabase.class, "note_database")
                        .build();
            }
        }
        return noteRoomInstance;
    }
}
