package com.example.noteme_test;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class CreateNewNote extends AppCompatActivity {
    Spinner mySpinner;
    Button buttonDone, buttonBack;
    TextView title, subtitle, note_Context;
    LinearLayout imageAdd;
    ImageView image;
    Activity activity;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

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

        imageAdd = findViewById(R.id.layoutAddImage);
        image  = findViewById(R.id.image2);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(CreateNewNote.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }else {
                    selectImage();
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

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        /*if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                selectImage();
            }else{

                Toast.makeText(this,"Permission Denied" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        image.setImageBitmap(bitmap);
                        image.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
