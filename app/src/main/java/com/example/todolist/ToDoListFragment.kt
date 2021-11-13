package com.example.todolist

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ToDoListFragment"
const val EXTRA_ID = "ARG"
const val DATE_FORMAT = "dd/MMMM/yyyy"

class ToDoListFragment : Fragment() {
    private lateinit var toDoRecyclerView: RecyclerView
    private val toDoListViewModel: ToDoViewModel by lazy {
        ViewModelProvider(this).get(ToDoViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_item_to_do, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_list_to_do,
            container,
            false
        )
        toDoRecyclerView = view.findViewById(R.id.to_do_recycler_view) as RecyclerView
        toDoRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoListViewModel.LiveDataTasks.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    updateUI(tasks)
                }
            }
        )
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_task -> {
                val taskFragment = TaskFragment()
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, taskFragment).addToBackStack("").commit()
                }
                true
            }
            R.id.sort -> {
                toDoListViewModel.orderByReminderDate().observe(
                    viewLifecycleOwner, Observer {
                        updateUI(it)
                    }
                )
                true
            }
            R.id.filter -> {
                toDoListViewModel.filterByDone().observe(
                    viewLifecycleOwner, Observer {
                        updateUI(it)

                    }
                )
                true
            }
            R.id.filter_undone-> {
                toDoListViewModel.filterByUnDone().observe(
                    viewLifecycleOwner, Observer {
                        updateUI(it)

                    }
                )
                true
            }
        else -> return super.onOptionsItemSelected(item)
    }
}

    private fun updateUI(toDos: List<ToDo>) {
        val adapter = ToDoAdapter(toDos)
        toDoRecyclerView.adapter = adapter
    }

    private inner class ToDoHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var task: ToDo
        private val isDoneImageView: ImageView = itemView.findViewById(R.id.is_done)
        val toDoTitleList: TextView = itemView.findViewById(R.id.to_do_title_list)
        val entryDateList: TextView = itemView.findViewById(R.id.entry_date_list)
        val reminderDateList: TextView = itemView.findViewById(R.id.reminder_date_list)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(toDo: ToDo) {
            this.task = toDo
            val date = Date()
            toDoTitleList.text = this.task.titleToDo
            val time = SimpleDateFormat(DATE_FORMAT)
            entryDateList.text = time.format(task.entryDate)
            reminderDateList.text = time.format(task.reminderDate)

            if (toDo.isDone) {
                 isDoneImageView.setImageResource(R.drawable.isdone)
            } else if (date.before(toDo.reminderDate)) {
                 isDoneImageView.setImageResource(R.drawable.background)
            }else{
                 isDoneImageView.setImageResource(R.drawable.not_done)
             }
        }

        override fun onClick(view: View?) {
            val args = Bundle()
            args.putSerializable(EXTRA_ID, task.id)
            val taskFragment = TaskFragment()
            taskFragment.arguments = args
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, taskFragment)
                    .addToBackStack("")
                    .commit()
            }
        }
    }

    private inner class ToDoAdapter(var toDos: List<ToDo>) : RecyclerView.Adapter<ToDoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_to_do, parent, false)
            return ToDoHolder(view)
        }

        override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
            val toDo = toDos[position]
            holder.bind(toDo)
        }

        override fun getItemCount() = toDos.size

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}

