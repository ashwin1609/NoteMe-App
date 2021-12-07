package com.example.noteme_test;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
    private static final String Column_Color = "Note_Color";
    private static final String Column_imagePath = "Note_Image";

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
                Column_Context + " TEXT," +
                Column_Color + " TEXT," +
                Column_imagePath + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    void addNote(String title, String subtitle, String note_Context, String note_color, String note_image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_Title, title);
        contentValues.put(Column_Subtitle, subtitle);
        contentValues.put(Column_Context, note_Context);
        contentValues.put(Column_Color, note_color);
        contentValues.put(Column_imagePath, note_image);


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

    Cursor getSearchData( String target) {
        String query = "SELECT * FROM " + Table_Name + " WHERE " + Column_Title +" Like " + "'" +target + "%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateDatabase(String id, String title, String subtitle, String note_context, String note_color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_Title , title);
        contentValues.put(Column_Subtitle , subtitle);
        contentValues.put(Column_Context , note_context);
        contentValues.put(Column_Color , note_color);
        long result = db.update (Table_Name, contentValues , Column_ID +"=?", new String[] {id} );

        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println(" the result is :"+ result);
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    void DeleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete (Table_Name, Column_ID +"=?", new String[] {id} );
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println(" the result is :"+ result);
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void exportJSONFile() throws JSONException {
        Cursor cursor = getData();
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        while(cursor.moveToNext()) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(Column_ID, cursor.getString(0));
            jsonObj.put(Column_Title, cursor.getString(1));
            jsonObj.put(Column_Subtitle, cursor.getString(2));
            jsonObj.put(Column_Context, cursor.getString(3));
            jsonObj.put(Column_Color, cursor.getString(4));
            jsonObj.put(Column_imagePath, cursor.getString(5));
            jsonArray.put(jsonObj);
        }
        obj.put("notes", jsonArray);
        try {
            FileOutputStream fos = context.openFileOutput("notes.json", MODE_PRIVATE);
            fos.write(obj.toString().getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "JSON File Exported Successfully!", Toast.LENGTH_SHORT).show();
    }
}
