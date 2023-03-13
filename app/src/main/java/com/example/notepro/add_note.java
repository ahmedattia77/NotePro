package com.example.notepro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add_note extends AppCompatActivity {

    public final int ADD_RESULT_CODE = 13;
    public final int EDIT_RESULT_CODE = 14;


    private TextInputEditText title, description;
    private ImageView add;
    private TextView date;
    private ImageView delete;
    private Boolean result;
    private int noteId ;

    private DataBase dataBase = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.addNote_title_et);
        description = findViewById(R.id.addNote_description_et);
        add = findViewById(R.id.addNote_imageAdd_tv);
        delete = findViewById(R.id.addNote_imageDelete_tv);
        date = findViewById(R.id.addNote_date_tv);




        Intent intent = getIntent();
        noteId = intent.getIntExtra(MainActivity.NOTE_KEY ,-1);


        String title_ = title.getText().toString();
        String description_ = description.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm ");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

                String currentDateAndTime = sdf.format(new Date());


                if (noteId == -1){

                    // add

                    delete.setImageResource(R.drawable.baseline_keyboard_backspace_24);
                    date.setText(currentDateAndTime);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String title_ = title.getText().toString();
                            String description_ = description.getText().toString();

                            if (title_.isEmpty())
                                title_ = description_.substring(0, description_.indexOf(' '));
                            else if (description_.isEmpty())
                                description_ = title_;
                            else
                                finish();

                            Note note = new Note(title_ , description_ ,currentDateAndTime);
                            result = dataBase.insertNOTE(note);
                            if (result == true){
                                setResult(14, null);
                                finish();
                            }else
                                Toast.makeText(add_note.this, "insertion field", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else
                {
                    // edit
                    delete.setVisibility(View.VISIBLE);
                    date.setVisibility(View.GONE);

                            Note note = new Note();
                            note = dataBase.getNote(noteId);
                            title.setText(note.getTitle());
                            description.setText(note.getDescription());
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String t = title.getText().toString();
                            String d = description.getText().toString();
                            Note note = new Note(t , d , currentDateAndTime);
                            note.setId(noteId);
                            dataBase.select(note);
                            setResult(13, null);
                            finish();
                        }
                    });
                }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                note.setId(noteId);
                dataBase.delete(note);
                setResult(12 ,null);
                finish();
            }
        });

    }

//    private String firstBlockOfText (String text){
//        String result = "";
//        assert (!text.isEmpty());
//
//        for (int i =0; i < text.length(); i++){
//            if (text != " ")
//
//
//        }
//
//
//        if (!result.isEmpty())
//              return result;
//        return "";
//    }

}