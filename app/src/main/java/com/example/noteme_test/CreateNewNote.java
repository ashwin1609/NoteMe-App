package com.example.noteme_test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class CreateNewNote extends AppCompatActivity {
    Spinner mySpinner;
    Button buttonDone, buttonBack;
    TextView title, subtitle, note_Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        title = findViewById(R.id.Title);
        subtitle = findViewById(R.id.Subtitle);
        note_Context = findViewById(R.id.type_notes);
        buttonDone = findViewById(R.id.button_done);
        buttonBack = findViewById(R.id.button_back);

        mySpinner =  findViewById(R.id.dropDown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateNewNote.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dropDrownMenu));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note_color;

                if(title.getText().toString().trim().length() == 0){
                    title.setError("Title missing");
                }else{
                    MyDatabase database = new MyDatabase(CreateNewNote.this);
                    note_color = getColor(mySpinner.getSelectedItem().toString().trim());
                    database.addNote(title.getText().toString().trim(),
                            subtitle.getText().toString().trim(),
                            note_Context.getText().toString().trim(),
                            note_color);
                    Intent intent = new Intent(CreateNewNote.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewNote.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getColor( String color ){
        String temp = " ";
        if( color.equals("Yellow")){
            temp = "#ffff99";
        } else if( color.equals("Purple")){
            temp = "#cc99ff";
        } else if( color.equals("Red")){
            temp = "#ff9999";
        } else if( color.equals("Green")){
            temp = "#b3e6b3";
        } else {
            temp = "#d9d9d9";
        }
        return temp;
    }


}
