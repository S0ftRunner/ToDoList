package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

// в данном интерфейсе мы просто задаем правила того, как будет работать наша бд


@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();
    // именно здесь надо указать коллекию List, чтобы ROOM корректно работал, также надо присвоить
    // тип данных Single, чтобы во время потока в RxJava мы могли получить значения


    // нужно для вставления элементов, а также, чтобы не было конфликтов по id
    @Insert
    Completable addNote(Note note); // completable нужно для того, чтобы понять, завершилась ли работа по вставке или нет


    @Query("DELETE FROM notes WHERE id = :id") // происходит обращение к удалению через id
    Completable removeNote(int id);
}
