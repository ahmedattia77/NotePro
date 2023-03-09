package com.example.notepro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addButton;
    private RecycleAdapterOnClickListener recycleAdapterOnClickListener;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList <Note> notes;

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

        notes = getNotes();

        adapter = new Adapter(notes, new RecycleAdapterOnClickListener() {
            @Override
            public void inClickItem(int noteId) {
                Toast.makeText(MainActivity.this, noteId+ "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext() , add_note.class);
                intent.putExtra( NOTE_KEY,noteId);
                startActivityForResult(intent , ADD_REQUEST_CODE);
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

    private ArrayList<Note> getNotes() {
        ArrayList<Note> list = new ArrayList<>();

        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == 13 ) {
            DataBase dataBase = new DataBase(getBaseContext());
            ArrayList<Note> notes = new ArrayList<>();
            notes = dataBase.getNotes();
            adapter.setNotes(notes);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "added/modified successfully", Toast.LENGTH_SHORT).show();
        }
    }
}