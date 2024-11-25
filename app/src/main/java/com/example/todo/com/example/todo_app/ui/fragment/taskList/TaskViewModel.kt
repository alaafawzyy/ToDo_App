package com.example.todo.com.example.todo_app.ui.fragment.taskList

import android.icu.util.Calendar
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_app.database.TaskdataBase
import com.example.todo_app.database.model.Tasks

class TaskViewModel:ViewModel() {
    val tasksLiveData=MediatorLiveData<List<Tasks>>()
    lateinit var calender: Calendar
    fun getTask(){
        val ubdatedate = TaskdataBase.getInctace().getTasksDao()
            .GetTaskByDate(calender.time)
        tasksLiveData.value=ubdatedate
    }
}