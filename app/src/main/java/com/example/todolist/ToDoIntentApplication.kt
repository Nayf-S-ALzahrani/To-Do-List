package com.example.todolist

import android.app.Application
import com.example.todolist.database.TaskRepository

class ToDoIntentApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}