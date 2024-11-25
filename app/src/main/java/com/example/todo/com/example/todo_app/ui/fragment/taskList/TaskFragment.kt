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

class TaskFragment:Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var adabter: TaskAdapter
     lateinit var viewModel: TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTasksBinding.inflate(inflater)
        viewModel=ViewModelProvider(this).get(TaskViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adabter = TaskAdapter(null)
        obseveLivedata()
        val editTaskFragment= EditTaskFragment()
        val bundle=Bundle()


        adabter.setOnTaskClickListener{
            bundle.putParcelable(Consstant.PassedTask,it)
            editTaskFragment.arguments=bundle
            if(activity==null) return@setOnTaskClickListener
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,editTaskFragment)
                .commit()
        }
        adabter.setOnTaskClickListener{
         TaskdataBase.getInctace().getTasksDao().DeleteTask(it)
        }


        viewModel.calender = Calendar.getInstance()
        binding.rvTasks.adapter = adabter
        var item = TaskdataBase.getInctace().getTasksDao().GetTask()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val year = date.year
            val month = date.month - 1
            val day = date.day
            //بهمل التايم علشان بيحسب كل حاجه بال ثواني وبيعمل مشاكل
            deleteTime()
            viewModel.calender.set(Calendar.YEAR, year)
            viewModel.calender.set(Calendar.MONTH, month)
            viewModel.calender.set(Calendar.DAY_OF_MONTH, day)
           viewModel.getTask()

        }
        adabter.ubdateTask(item.toMutableList())
        binding.calendarView.selectedDate= CalendarDay.today()

    }

    private fun deleteTime() {
            viewModel.calender.set(java.util.Calendar.MINUTE,0)
           viewModel.calender. set(java.util.Calendar.MILLISECOND,0)
           viewModel.calender.set(java.util.Calendar.SECOND,0)
            viewModel.calender.set(java.util.Calendar.HOUR_OF_DAY,0)

    }
    fun obseveLivedata(){
        viewModel.tasksLiveData.observe(viewLifecycleOwner){
                adabter.ubdateTask(it.toMutableList())
        }
    }
fun getTask(){
    viewModel.getTask()
}



}
