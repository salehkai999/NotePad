package com.saleh.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class AddEditActivity extends AppCompatActivity {

    EditText titleText;
    EditText detailsText;
    Note currentNote = null;
    Note note = new Note();
    private int index = -1;
    private static final String TAG = "AddEditActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        titleText = findViewById(R.id.noteTitle);
        detailsText = findViewById(R.id.detailsText);
        detailsText.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        if(intent.hasExtra((Note.class.getName())) && intent.hasExtra("index")){
            currentNote = (Note) intent.getSerializableExtra(Note.class.getName());
            index = intent.getIntExtra("index",index);
            if(currentNote != null) {
                titleText.setText(currentNote.getTitle());
                detailsText.setText(currentNote.getDetails());
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save :
                //Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                saveNote();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        //Note note = new Note();
        if(titleText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Untitled Note can't be saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Log.d(TAG, "saveNote: "+titleText.getText().toString());
            note.setTitle(titleText.getText().toString());
            note.setDetails(detailsText.getText().toString());
            note.setDateEdited(new Date());
            if(currentNote != null) {
                if(!currentNote.getDetails().equals(note.getDetails()) || !currentNote.getTitle().equals(note.getTitle())) {
                    currentNote.setDateEdited(new Date());
                    sendResults(note);
                }
                else
                    sendResults(currentNote);
            }

            sendResults(note);
            //Intent intent = new Intent();
            //intent.putExtra("TITLE",titleText.getText());
            //intent.putExtra("newNote",note);
            //setResult(Activity.RESULT_OK,intent);
            //finish();
        }
    }

    private void sendResults(Note newNote) {
        Log.d(TAG, "sendResults: "+newNote.toString());
        Intent intent = new Intent();
        //intent.putExtra("TITLE",titleText.getText());
        intent.putExtra("newNote",newNote);
        if(currentNote != null)
            intent.putExtra("oldNote",currentNote);
        //intent.putExtra("index",index);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        note.setTitle(titleText.getText().toString());
        note.setDetails(detailsText.getText().toString());
        note.setDateEdited(new Date());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your note isn't saved!!");
        builder.setMessage("Save " + titleText.getText().toString() + " ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveNote();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(currentNote != null)
                    sendResults(currentNote);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();

        if(titleText.getText().toString().trim().isEmpty()) {//|| currentNote != null) {
            if (!detailsText.getText().toString().trim().isEmpty())
                Toast.makeText(this, "Untitled Note can't be saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            if(currentNote != null) {
                if(currentNote.getDetails().equals(note.getDetails()) && currentNote.getTitle().equals(note.getTitle())) {
                    //currentNote.setDateEdited(new Date());
                    saveNote();
                    //alertDialog.show();
                }
                else {
                    alertDialog.show();
                    //sendResults(currentNote);
                    //finish();
                }
            }
            else {
            alertDialog.show(); }
        }
    }

    private boolean isChanged() {
        return true;
    }
}