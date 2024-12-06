package com.example.todo.com.example.todo_app.ui.fragment.taskList
import android.util.Log
import java.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_app.database.TaskdataBase
import com.example.todo.com.example.todo_app.database.model.Tasks


class TaskViewModel : ViewModel() {
    lateinit var calender: Calendar

    private val _tasksLiveData = MutableLiveData<List<Tasks>>()
    val tasksLiveData: LiveData<List<Tasks>> get() = _tasksLiveData


    init {
        calender = Calendar.getInstance()
        getTask()
    }

    fun getTask() {
        val updatedTasks = TaskdataBase.getInctace().getTasksDao()
            .GetTask()
            //.GetTaskByDate(calender.time)
        Log.w("a","alaa78$updatedTasks")
        _tasksLiveData.value = updatedTasks
    }
}
