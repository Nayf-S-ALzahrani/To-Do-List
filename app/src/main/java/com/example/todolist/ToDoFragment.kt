package com.example.todolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView


class ToDoFragment : Fragment() {
    private lateinit var toDo: ToDo
    private lateinit var titleToDo: TextView
    private lateinit var description: TextView
    private lateinit var entryDate: Button
    private lateinit var reminderDate: Button
    private lateinit var isDoneBox: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDo = ToDo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_to_do,
            container,
            false
        )
        titleToDo = view.findViewById(R.id.to_do_title) as EditText
        description = view.findViewById(R.id.description_title) as EditText
        entryDate = view.findViewById(R.id.entry_date) as Button
        reminderDate = view.findViewById(R.id.reminder_date) as Button
        isDoneBox = view.findViewById(R.id.is_done_box) as CheckBox


        entryDate.apply {
            text = toDo.entryDate.toString()
            isEnabled = false
        }
        reminderDate.apply {
            text = toDo.reminderDate.toString()
            isEnabled = false
        }


        return view
    }

    override fun onStart() {
        super.onStart()
            val titleWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    toDo.titleToDo = s.toString()
                    toDo.description = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            }
        }
    }


