package com.saleh.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private static final String TAG = "AddEditActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        titleText = findViewById(R.id.noteTitle);
        detailsText = findViewById(R.id.detailsText);

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
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                saveNote();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        Note note = new Note();
        if(titleText.getText().toString().trim().isEmpty())
            Toast.makeText(this, "Untitled Note", Toast.LENGTH_SHORT).show();
        else {
            Log.d(TAG, "saveNote: "+titleText.getText().toString());
            note.setTitle(titleText.getText().toString());
            note.setDetails(detailsText.getText().toString());
            note.setDateEdited(new Date());
            Intent intent = new Intent();
            //intent.putExtra("TITLE",titleText.getText());
            intent.putExtra("newNote",note);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

}