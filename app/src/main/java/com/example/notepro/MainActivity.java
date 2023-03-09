package com.example.notepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
            }
        });

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private ArrayList<Note> getNotes() {
        ArrayList<Note> list = new ArrayList<>();

        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        list.add(new Note("First" ," hello people it's first note :)" , "12:30AM , 2022"));
        return list;
    }
}