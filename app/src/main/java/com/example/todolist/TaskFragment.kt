package com.example.todolist
import androidx.lifecycle.Observer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.projecttodo.DatePickerDialogueFragment
import java.util.*

private const val TAG = "ToDoFragment"
private const val ARG_TODO_ID = "toDo_id"
private const val ARG_TASK_ID = "task_id"
private const val TASK_DATE_KEY = "task_date"

class TaskFragment : Fragment(), DatePickerDialogueFragment.DatePickerCallback, View.OnClickListener {
    private lateinit var toDo: ToDo
    private lateinit var titleToDo: EditText
    private lateinit var description: EditText
    private lateinit var entryDate: Button
    private lateinit var reminderDate: Button
    private lateinit var saveBtn:Button
    private lateinit var isDone: CheckBox

    private val fragmentViewModel by lazy { ViewModelProvider(this).get(ToDoViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDo = ToDo()
        arguments?.let {
            val taskId: UUID = it.getSerializable(EXTRA_ID) as UUID
            fragmentViewModel.loadTask(taskId)
        }

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
        setHasOptionsMenu(true)
        titleToDo = view.findViewById(R.id.to_do_title) as EditText
        description = view.findViewById(R.id.description_title) as EditText
        entryDate = view.findViewById(R.id.entry_date) as Button
        reminderDate = view.findViewById(R.id.reminder_date) as Button
        saveBtn = view.findViewById(R.id.save_btn) as Button
        isDone = view.findViewById(R.id.is_done_box) as CheckBox


        entryDate.apply {
            text = toDo.entryDate.toString()
            isEnabled = false
        }
        reminderDate.apply {
            text = toDo.reminderDate.toString()
            isEnabled = true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewModel.taskLiveData.observe(
            viewLifecycleOwner,
            Observer { toDo ->
                toDo?.let {
                    this.toDo = toDo
                    updateUI()
                }
            }
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_to_do, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {
                fragmentViewModel.delete(toDo)
                parentFragmentManager.popBackStackImmediate()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        if (toDo.titleToDo.isBlank()) {
            Toast.makeText(context, "Enter Title", Toast.LENGTH_LONG).show()
        } else {
            fragmentViewModel.updateTask(toDo)
        }

    }

    private fun updateUI(){
        titleToDo.setText(toDo.titleToDo)
        description.setText(toDo.description)
        entryDate.text = toDo.entryDate.toString()
        reminderDate.text = toDo.reminderDate.toString()
        isDone.apply {
            isChecked = toDo.isDone
            jumpDrawablesToCurrentState()
        }
    }

    override fun onDateSelected(date: Date) {
        toDo.reminderDate = date
        reminderDate.text = date.toString()
    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toDo.titleToDo = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                //i will do nothing
            }
        }

            val descriptionWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    toDo.description = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    //i will do nothing
                }
            }

        titleToDo.addTextChangedListener(titleWatcher)
        description.addTextChangedListener(descriptionWatcher)
        isDone.apply { setOnCheckedChangeListener { _, isChecked -> toDo.isDone = isChecked } }
        reminderDate.setOnClickListener(this)
        saveBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            reminderDate -> {
                val args = Bundle()
                args.putSerializable(TASK_DATE_KEY, toDo.reminderDate)
                DatePickerDialogueFragment().also {
                    it.arguments = args
                    it.setTargetFragment(this, 0)
                    it.show(this.parentFragmentManager, "date picker")
                }
            }
            saveBtn -> {
                if (!toDo.titleToDo.isBlank()) {
                    if (arguments == null) {
                        fragmentViewModel.addTask(toDo)
                        parentFragmentManager.popBackStackImmediate()
                    } else {
                        fragmentViewModel.updateTask(toDo)
                        parentFragmentManager.popBackStackImmediate()
                    }
                } else {
                    Toast.makeText(context, "Enter Title", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


