package com.example.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;

    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public void saveNote(Note note) {
        Disposable disposable = notesDao.addNote(note)
                .subscribeOn(Schedulers.io()) // для выполнения на фоновом потоке
                .observeOn(AndroidSchedulers.mainThread()) // переключаем на главный поток
                .subscribe(new Action() { // будет выполняться после завершения верхнего потока
                    @Override
                    public void run() throws Throwable {
                        Log.d("AddNoteViewModel", "subscribe");
                        shouldCloseScreen.postValue(true); // setvalue можем вызвать только на главном
                        // потоке,а postvalue на любом потоке
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("AddNoteViewModel", "save note error");
                    }
                });

        compositeDisposable.add(disposable); // добавляем в коллекцию
    }


    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    // onCleared вызывается при выходе из viewModel. subscribe возвращает тип disposable

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose(); // отменяем подписку у всех объектов dispose
    }
}
