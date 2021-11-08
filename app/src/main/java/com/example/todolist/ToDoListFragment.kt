package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "ToDoListFragment"

class ToDoListFragment : Fragment() {
    private lateinit var toDoRecyclerView:RecyclerView
    private var adapter:ToDoAdapter? = null
    private val toDoListViewModel: ToDoListViewModel by lazy {
        ViewModelProvider(this).get(ToDoListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total to do list: ${toDoListViewModel.toDos.size}")
    }
    companion object {
        fun newInstance(): ToDoListFragment {
            return ToDoListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_list_to_do,
        container,
        false)
        toDoRecyclerView = view.findViewById(R.id.to_do_recycler_view) as RecyclerView
        toDoRecyclerView.layoutManager=LinearLayoutManager(context)
        updateUI()
        return view
    }
    private fun updateUI(){
        val toDos = toDoListViewModel.toDos
        adapter = ToDoAdapter(toDos)
        toDoRecyclerView.adapter = adapter
    }

    private inner class ToDoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val toDoTitleList: TextView = itemView.findViewById(R.id.to_do_title_list)
        val descriptionTitle: TextView = itemView.findViewById(R.id.description_title_list)
        val entryDateList: TextView = itemView.findViewById(R.id.entry_date_list)
        val reminderDateList: TextView = itemView.findViewById(R.id.reminder_date_list)
        //val isDoneList:CheckBox = itemView.findViewById(R.id.is_done_box)
    }

    private inner class ToDoAdapter(var toDos: List<ToDo>) : RecyclerView.Adapter<ToDoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_to_do, parent, false)
            return ToDoHolder(view)
        }

        override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
            val toDo = toDos[position]
            holder.apply {
                toDoTitleList.text = toDo.titleToDo
                descriptionTitle.text = toDo.description
                entryDateList.text = toDo.entryDate.toString()
                reminderDateList.text = toDo.reminderDate.toString()
            }
        }

        override fun getItemCount() = toDos.size

    }
}

