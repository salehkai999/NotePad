package com.saleh.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}