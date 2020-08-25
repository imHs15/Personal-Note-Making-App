package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;


public class noteView extends AppCompatActivity {
    int noteNumber;
    int eraseNoteCheck;
    TextView lastEdited;
    EditText titleIn;
    EditText noteIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.save);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((Html.fromHtml("<font color='#000000'>Edit Note</font>")));

        lastEdited = (TextView) findViewById(R.id.lastEdited);
        titleIn = (EditText) findViewById(R.id.title);
        noteIn = (EditText) findViewById(R.id.note);
        Intent intent = getIntent();
        noteNumber = intent.getIntExtra("noteId",-1);
        eraseNoteCheck = noteNumber;
        if (noteNumber != -1) {
            titleIn.setText(MainActivity.title.get(noteNumber));
            noteIn.setText(MainActivity.note.get(noteNumber));
            lastEdited.setText(MainActivity.lastEdited.get(noteNumber));
        } else {
            MainActivity.note.add("");
            MainActivity.title.add("");
            MainActivity.lastEdited.add("");
            noteNumber = MainActivity.note.size() - 1;
        }
        noteIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.note.set(noteNumber, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.note);
                sharedPreferences.edit().putStringSet("note", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        titleIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.title.set(noteNumber, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.title);
                sharedPreferences.edit().putStringSet("title", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
        Date date = new Date();
        String currentDateTimeString = "Last Edited On: "+dateFormat.format(date)+"  ";
        MainActivity.lastEdited.set(noteNumber, currentDateTimeString);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = new HashSet<>(MainActivity.lastEdited);
        sharedPreferences.edit().putStringSet("lastEdited", set).apply();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (titleIn.getText().toString().isEmpty()||noteIn.getText().toString().isEmpty()) {
                Toast.makeText(this, "Title or Note cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finishAffinity();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if(eraseNoteCheck == -1) {
            MainActivity.note.remove(noteNumber);
            MainActivity.title.remove(noteNumber);
            MainActivity.lastEdited.remove(noteNumber);
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet<>(MainActivity.lastEdited);
            sharedPreferences.edit().putStringSet("lastEdited", set).apply();
            HashSet<String> set1 = new HashSet<>(MainActivity.title);
            sharedPreferences.edit().putStringSet("title", set1).apply();
            HashSet<String> set2 = new HashSet<>(MainActivity.note);
            sharedPreferences.edit().putStringSet("note", set2).apply();
        }
        startActivity(intent);
        finishAffinity();
    }
}