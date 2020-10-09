package com.saleh.notepad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    TextView noteTite;
    TextView noteDate;
    TextView noteDetails;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        noteTite = itemView.findViewById(R.id.titleTextView);
        noteDate = itemView.findViewById(R.id.dateText);
        noteDetails = itemView.findViewById(R.id.detailsText);
    }
}
