package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EnterNote extends AppCompatActivity {

    private EditText writeNote;

    private RadioButton lowRadioButton;
    private RadioButton mediumRadioButton;

    private Button buttonSave;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_note);


        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose)
                    finish();
            }
        });


        initViews();


        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveNote();
            }
        });
    }


    private void initViews()
    {
        writeNote = findViewById(R.id.editTextNote);

        lowRadioButton = findViewById(R.id.lowRadioButton);
        mediumRadioButton = findViewById(R.id.mediumRadioButton);

        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote()
    {
        String text = writeNote.getText().toString().trim();

        if (text.isEmpty()) // если текст пустой, то высвечиваем ворнинг
        {
            Toast.makeText(EnterNote.this, getString(R.string.alert), Toast.LENGTH_SHORT).show();
        }

        else {
            int priority = getPriority();
            Note note = new Note(text, priority); // 0 для автогенерации id
            viewModel.saveNote(note);
        }


    }

    private int getPriority() { // тут выставляем значимость заметки с помощью радио кнопок
        int priority;

        if (lowRadioButton.isChecked())
        {
            priority = 0;
        }

        else if (mediumRadioButton.isChecked())
        {
            priority = 1;
        }

        else
        {
            priority = 2;
        }

        return priority;
    }


    public static Intent newIntent(Context context) // фабричный метод перехода к окну
    {
        return new Intent(context, EnterNote.class);
    }
}