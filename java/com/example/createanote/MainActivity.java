package com.example.createanote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton Fab;
    private List<Notes> notesList;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initalize();
        getNotes();

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNotes.class);
                startActivity(intent);
            }
        });
    }

    private void Initalize() {
        recyclerView = findViewById(R.id.NotesRecyclerView);
        Fab = findViewById(R.id.addNoteButton);
        notesList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.onItemClickedListener() {
            @Override
            public void onItemClick(int Position) {
                  UpdateItem(Position);
            }

            @Override
            public void onDeleteClick(int Position) {
                RemoveItem(Position);
            }
        });
    }

    private void getNotes() {

        class GetNotesTask extends AsyncTask<Void, Void, List<Notes>>
        {

            @Override
            protected List<Notes> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).notesDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Notes> notes) {
                super.onPostExecute(notes);
                if(notesList.size()==0)
                {
                    notesList.addAll(notes);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    notesList.add(0, notes.get(0));
                    adapter.notifyItemInserted(0);
                }
                recyclerView.smoothScrollToPosition(0);
            }
        }

    new GetNotesTask().execute();
        adapter.notifyDataSetChanged();
    }

    public void RemoveItem(int Position)
    {
        Notes note = notesList.get(Position);
        notesList.remove(Position);

        class deleteNoteTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                NotesDatabase.getDatabase(MainActivity.this).notesDao().DeleteNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
            }
        }
        new deleteNoteTask().execute();

        adapter.notifyItemRemoved(Position);
    }

    public void UpdateItem(int Position)
    {
        Notes note = notesList.get(Position);

        updateTwoVala(note);
        updateOneVala(note);


        class UpdateDatas extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                  notesList.set(Position, note);
                    NotesDatabase.getDatabase(MainActivity.this).notesDao().UpdateNote(note);
                    return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                adapter.notifyItemChanged(Position);
            }
        }
        new UpdateDatas().execute();
        adapter.notifyItemChanged(Position);
    }

    private void updateTwoVala(Notes note) {
        AlertDialog.Builder builderTwo = new AlertDialog.Builder(MainActivity.this);
        EditText editNote = new EditText(MainActivity.this);
        builderTwo.setMessage("Change Note");
        builderTwo.setView(editNote);
        builderTwo.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Update Note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteText = editNote.getText().toString();
                if(!TextUtils.isEmpty(noteText))
                {
                    note.setText(noteText);
                }
            }
        }).create().show();
    }

    private void updateOneVala(Notes note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        EditText editTitle = new EditText(MainActivity.this);
        builder.setMessage("change Title");
        builder.setView(editTitle);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Update Title", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTitle.getText().toString();
                if(!TextUtils.isEmpty(title))
                {
                    note.setTitle(title);
                }
            }
        }).create().show();
    }


}