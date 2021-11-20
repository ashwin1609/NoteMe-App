package com.example.noteme_test;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title , subtitle, note_context;
    Button update_button, delete_button;
    String title_txt, subtitle_txt, note_context_txt, id, note_color;
    ConstraintLayout update_page;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title = findViewById(R.id.Title2);
        subtitle = findViewById(R.id.Subtitle2);
        note_context = findViewById(R.id.type_notes2);
        update_button = findViewById(R.id.button_update);
        update_page = findViewById(R.id.update_page);

        mySpinner =  findViewById(R.id.dropDown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UpdateActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dropDrownMenu));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        getIntentData();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title_txt);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabase database = new MyDatabase(UpdateActivity.this);
                note_color = getColor(mySpinner.getSelectedItem().toString().trim());
                title_txt = title.getText().toString().trim();
                subtitle_txt = subtitle.getText().toString().trim();
                note_context_txt = note_context.getText().toString().trim();
                database.updateDatabase(id,title_txt,subtitle_txt,note_context_txt, note_color);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete_button = findViewById(R.id.button_delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }
    public void getIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("subtitle") && getIntent().hasExtra("context") && getIntent().hasExtra("color")){
            id = getIntent().getStringExtra("id");
            title_txt = getIntent().getStringExtra("title");
            subtitle_txt = getIntent().getStringExtra("subtitle");
            note_context_txt = getIntent().getStringExtra("context");
            note_color = getIntent().getStringExtra("color");
            update_page.setBackgroundColor(Color.parseColor(note_color));

            // store the string
            title.setText(title_txt);
            subtitle.setText(subtitle_txt);
            note_context.setText(note_context_txt);


        }else {
            Toast.makeText(UpdateActivity.this, "No data.", Toast.LENGTH_SHORT).show();
        }
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

    void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete \"" + title_txt + " \" ?");
        builder.setMessage(" Are you you sure you wish to delete \"" + title_txt+ " \" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabase database = new MyDatabase(UpdateActivity.this);
                database.DeleteRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}