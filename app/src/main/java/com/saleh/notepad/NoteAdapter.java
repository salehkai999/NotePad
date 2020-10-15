package com.saleh.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private List<Note> noteList;
    private MainActivity mainActivity;

    public NoteAdapter(List<Note> noteList, MainActivity mainActivity) {
        this.noteList = noteList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row_layout,parent,false);
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.noteTite.setText(note.getTitle());
        holder.noteDate.setText(note.getDateEdited().toString());
        holder.noteDetails.setText(note.getDetails());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
