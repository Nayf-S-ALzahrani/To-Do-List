package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.ToDo
import java.util.*

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAllTask(): LiveData<List<ToDo>>

    @Query("SELECT * FROM todo WHERE id=(:id)")
    fun getTask(id: UUID): LiveData<ToDo?>

    @Update
    fun updateTasks(task: ToDo)

    @Insert
    fun addTasks(task: ToDo)

    @Delete
    fun deleteTasks(task: ToDo)
}