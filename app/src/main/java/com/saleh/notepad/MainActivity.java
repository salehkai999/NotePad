package com.saleh.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        NoteAdapter noteAdapter = new NoteAdapter(noteList,this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(int i=0;i<10;i++) {
            noteList.add(new Note(i+"","SSSSSAAAA",new Date()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_add:
                Toast.makeText(this, "EDIT ADD", Toast.LENGTH_SHORT).show();
                openEdit();
                return true;
            case R.id.info:
                Toast.makeText(this, "INFO", Toast.LENGTH_LONG).show();
                openInfo();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void openEdit() {

    }

    private void openInfo() {
        Intent intent = new Intent(this,InfoActivity.class);
        startActivity(intent);
    }

}
