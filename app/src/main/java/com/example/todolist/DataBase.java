package com.example.todolist;

import java.util.ArrayList;
import java.util.Random;

// этот класс был прототипом NoteDatabase, сейчас он никак не используется

public class DataBase {
    private ArrayList<Note> notes = new ArrayList<>();

    private static DataBase instance = null; // эта переменная нужна для реализации паттерна снизу

    public static DataBase getInstance() // реализация паттерна Singleton
    {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    private DataBase() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note" + i, random.nextInt(3));
            notes.add(note);
        }

    }

    public void add(Note note) { // добавление заметки
        notes.add(note);
    }

    public void remove(int id) { // удаление заметки
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId() == id) {
                notes.remove(note);
            }
        }
    }


    public ArrayList<Note> getNotes() { // получение копии списка базы данных
        return new ArrayList<>(notes); // возвращаем копию списка
    }
}
