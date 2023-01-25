package com.example.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes") // для базы данных
public class Note
{

    @PrimaryKey(autoGenerate = true) // нужен для того, чтобы id стал уникальным, также благодаря аргументу функции, сама программа будет генерировать уникальный id
    private int id;
    private String text;
    private int priority;

    public Note(int id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
    }

    @Ignore
    public Note(String text, int priority) { // мы можем использовать данный конструктор, а Room не будет его видеть
        this(0, text, priority);
    }

    public int getId()
    {
        return id;
    }

    public String getText()
    {
        return text;
    }

    public int getPriority()
    {
        return priority;
    }
}
