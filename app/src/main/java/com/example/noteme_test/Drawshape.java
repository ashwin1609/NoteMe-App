package com.example.noteme_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Drawshape extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintView paintView = new PaintView( Drawshape.this);
        setContentView(paintView);
    }
}