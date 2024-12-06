package com.example.todo.com.example.todo_app.ui.fragment.taskList


import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.FragmentTasksBinding
import com.example.todo.com.example.todo_app.ui.other.Consstant

import com.example.todo.com.example.todo_app.ui.adapter.TaskAdapter
import com.example.todo_app.database.TaskdataBase

import com.prolificinteractive.materialcalendarview.CalendarDay
import fragment.EditTaskFragment
class TaskFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var adapter: TaskAdapter
    lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TaskAdapter(mutableListOf())
        binding.rvTasks.adapter = adapter

        observeLiveData()

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            val year = date.year
            val month = date.month - 1
            val day = date.day

            deleteTime()
            viewModel.calender.set(Calendar.YEAR, year)
            viewModel.calender.set(Calendar.MONTH, month)
            viewModel.calender.set(Calendar.DAY_OF_MONTH, day)

            viewModel.getTask()
        }

        adapter.setOnTaskClickListener { task ->
            TaskdataBase.getInctace().getTasksDao().DeleteTask(task)
            viewModel.getTask() // تحديث بعد الحذف
        }

        binding.calendarView.selectedDate = CalendarDay.today()
    }

    private fun deleteTime() {
        viewModel.calender.set(Calendar.HOUR_OF_DAY, 0)
        viewModel.calender.set(Calendar.MINUTE, 0)
        viewModel.calender.set(Calendar.SECOND, 0)
        viewModel.calender.set(Calendar.MILLISECOND, 0)
    }

    private fun observeLiveData() {
        viewModel.tasksLiveData.observe(viewLifecycleOwner) { tasks ->
            adapter.ubdateTask(tasks.toMutableList())
        }
    }
}

