package com.example.todolist

import java.util.*

data class ToDo (val id :UUID= UUID.randomUUID(),
                 var titleToDo:String="",
                 var description:String="",
                 var entryDate : Date=Date(),
                 var reminderDate : Date=Date(),
                 //var isDoneBox:Boolean = false
)