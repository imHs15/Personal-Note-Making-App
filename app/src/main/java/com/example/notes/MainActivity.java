package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity{
    ListView listView;
    static ArrayAdapter arrayAdapter;
    static ArrayList<String> title = new ArrayList<String>();
    static ArrayList<String> note = new ArrayList<String>();
    static ArrayList<String> lastEdited = new ArrayList<String>();
    static String currentDateTimeString;
    SharedPreferences sharedPreferences;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.help) {
            Log.i("Item Selected", "Help");
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public void newNote(View view){
        Intent intent = new Intent(getApplicationContext(), noteView.class);
        intent.putExtra("noteId",-1);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
        Date date = new Date();
        currentDateTimeString = "Last Edited On: "+dateFormat.format(date)+"  ";
        Log.i("Date", currentDateTimeString);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        ListView listView = findViewById(R.id.listView);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Notes</font>"));

        HashSet<String> set1 = (HashSet<String>) sharedPreferences.getStringSet("note", null);
        HashSet<String> set2 = (HashSet<String>) sharedPreferences.getStringSet("title", null);
        HashSet<String> set3 = (HashSet<String>) sharedPreferences.getStringSet("lastEdited", null);


        if (set1 == null && set2 == null) {

        } else {
            note = new ArrayList(set1);
            title = new ArrayList(set2);
            lastEdited = new ArrayList(set3);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, title);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),noteView.class);
                if(title.get(i) == note.get(i)){
                    title.set(i,"");
                }
                intent.putExtra("noteId",i);
                startActivity(intent);
                finishAffinity();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                note.remove(itemToDelete);
                                title.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set1 = new HashSet<>(MainActivity.note);
                                HashSet<String> set2 = new HashSet<>(MainActivity.title);
                                sharedPreferences.edit().putStringSet("note", set1).apply();
                                sharedPreferences.edit().putStringSet("title", set2).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });
    }
}