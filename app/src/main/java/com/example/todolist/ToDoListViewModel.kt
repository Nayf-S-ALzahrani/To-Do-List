package com.example.todolist

import androidx.lifecycle.ViewModel

class ToDoListViewModel : ViewModel() {
    val toDos = mutableListOf<ToDo>()

    init {
        for (i in 0 until 100) {
            val toDo = ToDo()
            toDo.titleToDo = "to do #$i"
            toDo.description = "description #$i"
            toDos += toDo
        }
    }
}