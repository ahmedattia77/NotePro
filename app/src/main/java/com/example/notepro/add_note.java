package com.example.notepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add_note extends AppCompatActivity {

    private TextInputEditText title, description;
    ImageView add;
    private Boolean result;
    private int noteId = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.addNote_title_et);
        description = findViewById(R.id.addNote_description_et);
        add = findViewById(R.id.addNote_imageAdd_tv);
        //date = findViewById(R.id.addNote_date_et);



        Intent intent = getIntent();
        noteId = intent.getIntExtra(MainActivity.NOTE_KEY ,-1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title_ = title.getText().toString();
                String description_ = description.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateAndTime = sdf.format(new Date());

                Toast.makeText(add_note.this, title_, Toast.LENGTH_SHORT).show();

                if (noteId == -1){
                    // add
                    DataBase dataBase = new DataBase(getBaseContext());
                    Note note = new Note(title_ , description_ ,currentDateAndTime);
                    result = dataBase.insertNOTE(note);
                    if (result == true){
                        Toast.makeText(add_note.this, "add successfully", Toast.LENGTH_SHORT).show();
                        setResult(13, null);
                        finish();
                    }else
                        Toast.makeText(add_note.this, "insertion field", Toast.LENGTH_SHORT).show();

                }else{
                    // edit

                }
            }
        });





    }
    private void turnOnField (){
        //description.setVisibility(true);
    }
}