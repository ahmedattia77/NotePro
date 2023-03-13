package com.example.notepro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addButton;
    private RecycleAdapterOnClickListener recycleAdapterOnClickListener;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList <Note> notes;
    DataBase dataBase = new DataBase(this);

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 11;
    public static final String NOTE_KEY = "note_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recyclerView);
        addButton  = findViewById(R.id.main_floating_bt);


        notes = new ArrayList<>();
        DataBase dataBase = new DataBase(this);
        notes = dataBase.getNotes();
        Collections.reverse(notes);
        adapter = new Adapter(notes, new RecycleAdapterOnClickListener() {
            @Override
            public void onClickItem(int noteId) {
                Intent intent = new Intent(getBaseContext(), add_note.class);
                intent.putExtra(NOTE_KEY, noteId);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext() , add_note.class);
                startActivityForResult(intent , ADD_REQUEST_CODE);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.mainMenu_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Note> notes = new ArrayList<>();
                notes = dataBase.searchNotes(newText);
                Collections.reverse(notes);
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ArrayList<Note> notes = new ArrayList<>();
                notes = dataBase.getNotes();
                Collections.reverse(notes);
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenu_deleteAll:
                new AlertDialog.Builder(this)
                        .setTitle("Delete All Nodes !")
                        .setMessage("Are you sure you want to delete All nodes?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dia
                        // log is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                dataBase.deleteAll();
                                ArrayList<Note> notes = new ArrayList<>();
                                notes = dataBase.getNotes();
                                adapter.setNotes(notes);
                                adapter.notifyDataSetChanged();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == ADD_REQUEST_CODE && resultCode == 14 || requestCode == EDIT_REQUEST_CODE && resultCode == 13 ) {
        DataBase dataBase = new DataBase(getBaseContext());
        ArrayList<Note> notes = new ArrayList<>();
        notes = dataBase.getNotes();
        Collections.reverse(notes);
        adapter.setNotes(notes);
        adapter.notifyDataSetChanged();
//    }
    }
}