package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setTitle((Html.fromHtml("<font color='#000000'>How To?</font>")));
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> listItems = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        listItems.add("Create a new note");
        listItems.add("Save a note");
        listItems.add("Delete a note");
        listItems.add("Edit a note");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: new AlertDialog.Builder(Help.this)
                            .setIcon(R.drawable.help)
                            .setTitle("Create a new note")
                            .setMessage("In the Notes View click on + icon to create a new note")
                            .setPositiveButton("Close",null)
                            .show();
                    break;
                    case 1: new AlertDialog.Builder(Help.this)
                            .setIcon(R.drawable.help)
                            .setTitle("Save a note")
                            .setMessage("In the Edit Note View click on the ✓ to save your note")
                            .setPositiveButton("Close",null)
                            .show();
                        break;
                    case 2: new AlertDialog.Builder(Help.this)
                            .setIcon(R.drawable.help)
                            .setTitle("Delete a note")
                            .setMessage("In the Notes View long press the title of the note you want to delete")
                            .setPositiveButton("Close",null)
                            .show();
                        break;
                    case 3: new AlertDialog.Builder(Help.this)
                            .setIcon(R.drawable.help)
                            .setTitle("Edit a note")
                            .setMessage("In the Notes View click on the title of the note you want to edit")
                            .setPositiveButton("Close",null)
                            .show();
                        break;
                }
            }
        });
    }
}