package com.example.todonvvm.databases;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    static TodoRepository INSTANCE;
    TodoRoomDatabase db;
    TodoDao dao;

    public TodoRepository(Application application) {
        db = TodoRoomDatabase.getDatabase(application);
        dao = db.todoDao();
    }

    public static TodoRepository getRepository(Application application){
        if (INSTANCE == null){
            INSTANCE = new TodoRepository(application);
        }
        return INSTANCE;
    }

    //Methods from TodoDao

    public LiveData<List<Todo>> getAllTask(){
        return dao.getAllTasks();
    }

    public void deleteAll(){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void update(final Todo todo){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(todo);
            }
        });
    }

    public void insert(final Todo todo){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(todo);
            }
        });
    }
    public void delete(final Todo todo){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(todo);
            }
        });
    }

}

