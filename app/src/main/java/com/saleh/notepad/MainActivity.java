package com.saleh.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    private final List<Note> noteList = new ArrayList<>();
    private final List<Note> newNotes = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private static final String TAG = "MainActivity_SQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        noteAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadJSONDate();
        this.setTitle("Notes ("+noteList.size()+")");
        /*for(int i=0;i<10;i++) {
            noteList.add(new Note(i+"","SSSSSAAAA",new Date()));
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Saved!");
        saveNotesData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_add:
                //Toast.makeText(this, "EDIT ADD", Toast.LENGTH_SHORT).show();
                openEdit();
                return true;
            case R.id.info:
                //Toast.makeText(this, "INFO", Toast.LENGTH_LONG).show();
                openInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openEdit() {
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivityForResult(intent, 1);
    }

    private void openInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    private void loadJSONDate() {
        try {
            InputStream inputStream = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                Log.d(TAG, "loadJSONDate: " + line);
            }
            JSONArray jsonArray = new JSONArray(builder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Note note = new Note();
                note.setTitle(jsonObject.getString("title"));
                note.setDetails(jsonObject.getString("details"));
                note.setDateEdited(new Date(jsonObject.getString("date")));
                noteList.add(note);
            }
            Collections.sort(noteList);
            noteAdapter.notifyDataSetChanged();
        } catch (
                FileNotFoundException e) {
            Toast.makeText(this, "NO FILE", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNotesData() {
        if (noteList.size() != 0) {
            try {
                FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
                JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
                jsonWriter.beginArray();
                for (Note note : noteList) {
                    jsonWriter.setIndent("  ");
                    jsonWriter.beginObject();
                    jsonWriter.name("title").value(note.getTitle());
                    jsonWriter.name("details").value(note.getDetails());
                    jsonWriter.name("date").value(note.getDateEdited().toString());
                    jsonWriter.endObject();
                }
                jsonWriter.endArray();
                jsonWriter.close();
                Log.d(TAG, "saveNotesData: " + noteList.toString());
                //noteList.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, "No New Notes", Toast.LENGTH_SHORT).show();
    }


    private void updateTitle() {
        this.setTitle("Notes ("+noteList.size()+")");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode);
        if (resultCode == RESULT_OK) {
            Note newNote = (Note) data.getSerializableExtra("newNote");
            //int index = data.getIntExtra("index",-1);
            //Toast.makeText(this, getIntent().getStringExtra("Title"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onActivityResult: " + newNote.toString());
            if(data.hasExtra("oldNote")) {
                Note oldNote = (Note) data.getSerializableExtra("oldNote");
                noteList.remove(oldNote);
                noteList.add(newNote);
                Collections.sort(noteList);
                noteAdapter.notifyDataSetChanged();
            }
            else
            {
                noteList.add(newNote);
                Collections.sort(noteList);
                noteAdapter.notifyDataSetChanged();
            }
           updateTitle();
        }

    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        Note note = noteList.get(pos);
        //Toast.makeText(this, note.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,AddEditActivity.class);
        intent.putExtra(Note.class.getName(),note);
        intent.putExtra("index",pos);
        //noteList.remove(pos);
        startActivityForResult(intent,1);
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int pos = recyclerView.getChildLayoutPosition(v);
        Note note = noteList.get(pos);
        builder.setTitle("Delete"+note.getTitle());
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                noteList.remove(pos);
                noteAdapter.notifyDataSetChanged();
                updateTitle();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog alertDialog =builder.create();
        alertDialog.show();
        return true;
    }
}
