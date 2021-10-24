package com.example.noteme_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabase  extends SQLiteOpenHelper {

    private Context context;
    // naming the database
    private static final String DATABASE_NAME = "NoteMe.db";
    private static final int DATABASE_VERSION = 1;
    private static final String Table_Name = "MY_NOTES";
    private static final String Column_ID = "ID";
    private static final String Column_Title = "Note_Title";
    private static final String Column_Subtitle = "Note_SubTitle";
    private static final String Column_Context = "Note_Context";


    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + Table_Name +
                " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Title + " TEXT," +
                Column_Subtitle + " TEXT," +
                Column_Context + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    void addNote(String title, String subtitle, String note_Context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_Title, title);
        contentValues.put(Column_Subtitle, subtitle);
        contentValues.put(Column_Context, note_Context);

        long check_result = db.insert(Table_Name, null, contentValues);
        if (check_result == -1) {
            Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Note Added Successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getData() {
        String query = "SELECT * FROM " + Table_Name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
