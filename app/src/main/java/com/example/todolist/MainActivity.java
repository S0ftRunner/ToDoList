package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // объявляем всякие вью элементы
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton addNote;


    private NotesAdapter notesAdapter; // объявляем адаптер(нужен для RecyclerView)


    private MainViewModel viewModel; // объявляем viewModel для построения архитектуры MVVM


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // такой способ объявления вью модели позволяет при перевороте экрана не стереть данные
        // всегда надо использовать такой способ объявления вьюмодели
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        initViews();
        notesAdapter = new NotesAdapter();


        recyclerViewNotes.setAdapter(notesAdapter); // подключаем адаптер к нашему RecyclerView

        // здесь теперь будут всегда показываться изменения списка, крч этот метод отвечает за любые
        // изменения списка заметок
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        // класс для свайпа
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) { // все это для свайпа
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target
            ) {
                return false;
            } // вызывается в тот момент, когда мы хотим перенести элементы с одного места на другое


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                viewModel.remove(note); // работа с данными в данном классе не реализована, т.к все
                // работает через MVVM
            }
        });


        itemTouchHelper.attachToRecyclerView(recyclerViewNotes); // подключаем управление свайпом


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EnterNote.newIntent(MainActivity.this);
                startActivity(intent);
            }
        }); // кнопка для перехода в новое окно, чтобы добавить заметку

    }


    private void initViews() { // инициализируем вью элементы

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        addNote = findViewById(R.id.buttonAddNote);
    }
}