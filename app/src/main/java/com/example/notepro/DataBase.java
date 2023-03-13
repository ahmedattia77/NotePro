package com.example.notepro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    public static final String NAME_DS = "note";
    public static final int VERSION_DS = 1;

    public static final String TABLE_NAME = "note";
    public static final String NOTE_CLN_TABLE_ID = "id";
    public static final String NOTE_CLN_TABLE_TITLE = "title";
    public static final String NOTE_CLN_TABLE_DESCRIPTION = "description";
    public static final String NOTE_CLN_TABLE_DATE = "date";

    public DataBase(Context context){
        super(context,NAME_DS,null,VERSION_DS);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE table "+TABLE_NAME+" ("+NOTE_CLN_TABLE_ID+" INTEGER primary key autoincrement," +
                ""+NOTE_CLN_TABLE_TITLE+" TEXT , "+NOTE_CLN_TABLE_DESCRIPTION+" TEXT , "+NOTE_CLN_TABLE_DATE+" TEXT) ");
    }
/// for editing in dataBase
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertNOTE (Note notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOTE_CLN_TABLE_TITLE , notes.getTitle());
        values.put(NOTE_CLN_TABLE_DESCRIPTION , notes.getDescription());
        values.put(NOTE_CLN_TABLE_DATE , notes.getDate());

        long result = db.insert(TABLE_NAME,null,values);

        return result != -1;
    }

    public boolean select (Note notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOTE_CLN_TABLE_TITLE , notes.getTitle());
        values.put(NOTE_CLN_TABLE_DESCRIPTION , notes.getDescription());
        values.put(NOTE_CLN_TABLE_DATE , notes.getDate());

        String args [] ={String.valueOf(notes.getId())};
        int result = db.update(TABLE_NAME,values,"id=?",args);

        return result > 0;
    }


    public long count(){

        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db ,NAME_DS);
    }

    public boolean delete (Note notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        // sending dataInformation in the arr and send the array as a condition
        // we can't send the condition directly because it can be manipulated
        String args [] ={String.valueOf(notes.getId())};
        int result = db.delete(TABLE_NAME,"id=?",args);


        return result > 0;
    }


    public ArrayList<Note>  getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);

                Note note = new Note(id,title,description,date);
                notes.add(note);
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return notes;
    }
    public void  deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public Note getNote(int noteId) {

        Note note ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DataBase.TABLE_NAME+" WHERE "+DataBase.NOTE_CLN_TABLE_ID+"=?",new String[]{String.valueOf(noteId)});

        if (cursor.moveToFirst()){

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String date = cursor.getString(3);

            note = new Note(id,title,description,date);
            cursor.close();
            return  note;
        }

        return null;
    }

    public ArrayList<Note>  searchNotes(String modelSearch) {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        //String [] arg = {model};
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+NOTE_CLN_TABLE_TITLE+"=?",new String[]{modelSearch});

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);

                Note note = new Note(id,title,description ,date);
                notes.add(note);

            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return notes;
    }

}
