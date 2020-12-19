package com.example.createanote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.transform.Result;

public class CreateNotes extends AppCompatActivity {

   private EditText TitleView;
   private EditText NotesView;
   private ImageView BackButton;
   private ImageView SaveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        Initalize();

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNotes.this, MainActivity.class);
                startActivity(intent);
            }
        });


        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }


    private void Initalize() {
        TitleView = findViewById(R.id.TitleEdit);
        NotesView = findViewById(R.id.NotesEdit);
        BackButton = findViewById(R.id.backButton);
        SaveButton = findViewById(R.id.saveButton);
    }

    private void saveNote() {
            String title = TitleView.getText().toString();
            String textOfNote = NotesView.getText().toString();
            String time = new SimpleDateFormat("hh : mm a", Locale.getDefault()).format(Calendar.getInstance().getTime());
            Notes note = new Notes();

            if(TextUtils.isEmpty(title))
            {
                Toast.makeText(this, "Put some heading", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(textOfNote))
            {
                Toast.makeText(this, "Create a note first of all", Toast.LENGTH_SHORT).show();
            }
            else
            {
                note.setText(textOfNote);
                note.setTitle(title);
                note.setTime(time);
            }

            @SuppressLint("StaticFieldLeak")

            class saveNoteTask extends AsyncTask<Void, Void, Void>{


                @Override
                protected Void doInBackground(Void... voids) {
                    NotesDatabase.getDatabase(getApplicationContext()).notesDao().InsertNote(note);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    Intent intent1 = new Intent(CreateNotes.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }

            new saveNoteTask().execute();
    }
}