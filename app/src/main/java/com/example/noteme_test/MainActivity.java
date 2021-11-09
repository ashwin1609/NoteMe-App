package com.example.noteme_test;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDatabase database;
    ArrayList<String> note_id, note_Title, note_SubTitle, note_Context, note_color;
    CustomAdapter customAdapter;
    public EditText search_txt;
    public FloatingActionButton buttonAdd;
    public boolean displaySearchNote = false;
    public String target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_txt = findViewById(R.id.searchView);
        buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNewNote.class);
                startActivity(intent);
            }
        });

        database = new MyDatabase(MainActivity.this);
        note_id = new ArrayList<>();
        note_Title = new ArrayList<>();
        note_SubTitle = new ArrayList<>();
        note_Context = new ArrayList<>();
        note_color = new ArrayList<>();

        DisplayNote(target);
        callAdapter();

        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //customAdapter.cancelTimer();
            }
            @Override
            public void afterTextChanged(Editable s) {
                target = search_txt.getText().toString();
                displaySearchNote = true;
                DisplayNote(target);
                callAdapter();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public void callAdapter(){
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        customAdapter = new CustomAdapter(MainActivity.this,this,note_id,note_Title, note_SubTitle, note_Context, note_color);
        recyclerView.setAdapter(customAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void DisplayNote(String target) {
        Cursor cursor;
        if(displaySearchNote){
            cursor = database.getSearchData(target);
        } else {
            cursor = database.getData();
        }

        if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No available data in the database", Toast.LENGTH_SHORT).show();
        } else{
            note_id.clear();
            note_Title.clear();
            note_Context.clear();
            note_SubTitle.clear();
            note_color.clear();

            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_Title.add(cursor.getString(1));
                note_SubTitle.add(cursor.getString(2));
                note_Context.add(cursor.getString(3));
                note_color.add(cursor.getString(4));
            }
        }
    }
}
