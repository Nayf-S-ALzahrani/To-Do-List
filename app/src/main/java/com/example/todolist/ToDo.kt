package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class ToDo (@PrimaryKey val id :UUID= UUID.randomUUID(),
                 var titleToDo:String="",
                 var description:String="",
                 var entryDate : Date=Date(),
                 var reminderDate : Date=Date(),
                 var isDone:Boolean = false
)