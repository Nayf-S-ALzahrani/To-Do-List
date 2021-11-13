package com.example.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room.databaseBuilder
import com.example.todolist.ToDo
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "toDo-database"

class TaskRepository private constructor(context: Context) {
    private val database: ToDoDatabase = databaseBuilder(
        context.applicationContext,
        ToDoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val toDoDao = database.toDoDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllTask(): LiveData<List<ToDo>> = toDoDao.getAllTask()

    fun getTask(id: UUID): LiveData<ToDo?> {
        return toDoDao.getTask(id)
    }

    fun updateTasks(task: ToDo) {
        executor.execute { toDoDao.updateTasks(task) }
    }

    fun addTasks(task: ToDo) {
        executor.execute { toDoDao.addTasks(task) }
    }

    fun deleteTasks(task: ToDo) {
        executor.execute { toDoDao.deleteTasks(task) }
    }
    fun orderByReminderDate(): LiveData<List<ToDo>>{
        return toDoDao.orderByReminderDate()
    }

    fun filterByDone(): LiveData<List<ToDo>>{
        return  toDoDao.filterByDone()
    }

    fun filterByUnDone(): LiveData<List<ToDo>>{
        return toDoDao.filterByUnDone()
    }

    fun sortByTitle(): LiveData<List<ToDo>>{
        return toDoDao.sortByTitle()
    }

    fun sortComplete(): LiveData<List<ToDo>>{
        return toDoDao.sortComplete()
    }

    fun sortUnComplete(): LiveData<List<ToDo>>{
        return toDoDao.sortUnComplete()
    }

    companion object {
        private var INSTANCE: TaskRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
        }

        fun get(): TaskRepository {
            return INSTANCE ?: throw IllegalStateException("ToDoRepository must be initialized")
        }
    }
}