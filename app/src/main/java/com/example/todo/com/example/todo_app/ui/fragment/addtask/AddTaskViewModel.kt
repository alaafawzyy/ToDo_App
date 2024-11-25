package com.example.todo.com.example.todo_app.ui.fragment.addtask

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_app.database.TaskdataBase
import com.example.todo_app.database.model.Tasks


class AddTaskViewModel:ViewModel() {
    lateinit var calendar: Calendar
    val titleLiveData = MutableLiveData<String>()
    val descriptionLiveData = MutableLiveData<String>()
    val titleErrorLiveData = MutableLiveData<String?>()
    val descriptionErrorLiveData = MutableLiveData<String?>()
    val isDoneLiveData=MutableLiveData(false)


    fun addTask(){

      if(!ValidatField())return
        val task = Tasks(
            title = titleLiveData.value.toString(),
            description = descriptionLiveData.value.toString(),
            date = calendar.time,
            isDone = false
        )
        TaskdataBase.getInctace().getTasksDao().insertTask(task)
          Log.w("alaa","task is inserted  $task")
      isDoneLiveData.value=true
    }

    fun ValidatField(): Boolean {
        var isvalid=true
        if (titleLiveData.value.isNullOrBlank()) {
            titleErrorLiveData.value = "Required"
            isvalid= false
        } else
            titleErrorLiveData.value = null


        if (descriptionLiveData.value.isNullOrBlank()) {
            descriptionErrorLiveData.value = "Required"
            isvalid= false
        } else  descriptionErrorLiveData.value = null

        return isvalid
    }

}