package com.example.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import com.example.todolist.R.layout as layout1

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout1.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(layout1.activity_main)

        if (currentFragment == null) {
            val fragment = ToDoListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
