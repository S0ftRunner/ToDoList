package com.example.todolist;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>
{
    private List<Note> notes = new ArrayList<>(); // List нужен для LiveData

    private OnNoteClickListener onNoteClickListener; // объявляем объект

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    } // возвращаем копию массива

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) // в этом методе мы показываем как создавать view из макета
    {
        View view = LayoutInflater.from(parent.getContext()).inflate // получаем доступ к TextView из другого макета
                (
                        R.layout.note_item,
                        parent,
                        false
                );

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) // отвечает за установку каких - либо элементов вью элемента
    {
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText()); // устанавливаем текст для конкретной заметки

        int colorResId;

        switch(note.getPriority()) // здесь выдается цвет фона по важности
        {
            case 0:
                colorResId = android.R.color.holo_green_light;
                break;
            case 1:
                colorResId = android.R.color.holo_orange_light;
                break;
            default:
                colorResId = android.R.color.holo_red_light;
                break;
        }

        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);
        viewHolder.textViewNote.setBackgroundColor(color);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNoteClickListener != null)
                    onNoteClickListener.onNoteClick(note);
            }
        });

    }

    @Override
    public int getItemCount() // возвращает количество объектов, которые находятся в коллекции
    {
        return notes.size();
    }

    public void setNotes(List<Note> notes)
    {
        this.notes = notes;
        notifyDataSetChanged(); // метод сообщит адаптеру, что данные изменились
    }

    class NotesViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewNote;

        public NotesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }

    interface OnNoteClickListener {
        void onNoteClick(Note note);
    }
}
