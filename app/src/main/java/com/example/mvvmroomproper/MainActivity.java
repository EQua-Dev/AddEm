package com.example.mvvmroomproper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {


    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NEW_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private NoteListAdapter noteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteListAdapter = new NoteListAdapter(this, this);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            //        todo we write code to insert notes here
            final String note_id = UUID.randomUUID().toString();
            assert data != null;
            Note note = new Note(note_id, Objects.requireNonNull(data.getStringExtra(NewNoteActivity.NOTE_ADDED)));
            noteViewModel.insert(note);

            Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show();
        }else if (requestCode == UPDATE_NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            Note note = new Note(
                    Objects.requireNonNull(data.getStringExtra(EditNoteActivity.NOTE_ID)),
                    Objects.requireNonNull(data.getStringExtra(EditNoteActivity.UPDATED_NOTE)));
                    noteViewModel.update(note);

            Toast.makeText(getApplicationContext(),
                    R.string.updated,
                    Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(), R.string.not_saved, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        noteViewModel.delete(myNote);
    }
}
