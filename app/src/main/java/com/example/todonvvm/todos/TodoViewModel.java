package com.example.todonvvm.todos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.todonvvm.databases.Todo;
import com.example.todonvvm.databases.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private static TodoRepository mRepository;

    // Using LiveData and caching what getTodos returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel
    private LiveData<List<Todo>> mTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodos = mRepository.getAllTask();
    }

    LiveData<List<Todo>> getTodos() {
        return mTodos;
    }

    public void insert(Todo todo) {
        mRepository.insert(todo);
    }
    public void update(Todo todo) {
        mRepository.update(todo);
    }
    public static void delete(Todo todo){
        mRepository.delete(todo);
    }

}
