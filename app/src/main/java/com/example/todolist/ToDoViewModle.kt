package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskRepository
import com.example.todolist.database.ToDoDao
import java.util.*

class ToDoViewModel : ViewModel() {
    val taskRepository = TaskRepository.get()
    private val taskIdLiveData = MutableLiveData<UUID>()
    val LiveDataTasks = taskRepository.getAllTask()

    var taskLiveData:LiveData<ToDo?> =
        Transformations.switchMap(taskIdLiveData){
            taskRepository.getTask(it)
        }

    fun loadTask(taskId:UUID){
        taskIdLiveData.value = taskId
    }

    fun updateTask(task: ToDo) {
        taskRepository.updateTasks(task)
    }

    fun delete(task:ToDo){
        taskRepository.deleteTasks(task)
    }

    fun addTask(task: ToDo){
        taskRepository.addTasks(task)
    }

    fun orderByReminderDate(): LiveData<List<ToDo>>{
        return taskRepository.orderByReminderDate()
    }

    fun filterByDone(): LiveData<List<ToDo>>{
        return taskRepository.filterByDone()
    }

    fun filterByUnDone(): LiveData<List<ToDo>>{
        return taskRepository.filterByUnDone()
    }




}