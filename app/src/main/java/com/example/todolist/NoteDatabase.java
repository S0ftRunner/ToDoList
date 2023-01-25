package com.example.todolist;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// класс представляет из себя все приложение, также наследуется от
// контекста. Передаем именно Application, так как Context может уничтожиться при
// обновлении экрана или при завершении какого - то жц, произойдет утечка памяти
// Appplication будет жить пока приложение работает

@Database(entities = {Note.class}, version = 1, exportSchema = false)    // в фигурных скобках мы показываем то, какую структуру для бд будем использовать, а также, версию базу данных
public abstract class NoteDatabase extends RoomDatabase {

    private static final String DB_NAME = "notes.db"; // тут просто имя бд

    private static NoteDatabase instance = null; // ниже идет реализация singletone

    public static NoteDatabase getInstance(Application application){
        if (instance == null){
            instance = Room.databaseBuilder(
                    application,
                    NoteDatabase.class, // откуда будем брать базу данных
                    DB_NAME
            ).build(); // здесь будет лежать экземпляр класса notedatabase
        }
        return instance;
    }

    // singletone закончен

    public abstract NotesDao notesDao(); // метод возвращает экземпляр интерфейсного типа
}
