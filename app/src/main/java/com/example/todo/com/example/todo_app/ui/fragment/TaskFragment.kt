package fragment

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Database
import com.example.todo.R
import com.example.todo.databinding.FragmentTasksBinding
import com.example.todo.com.example.todo_app.ui.other.Consstant

import com.example.todo.com.example.todo_app.ui.adapter.TaskAdapter
import com.example.todo_app.database.TaskdataBase

import com.prolificinteractive.materialcalendarview.CalendarDay

class TaskFragment:Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var adabter: TaskAdapter
    lateinit var calender:Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adabter = TaskAdapter(null)
        val editTaskFragment=EditTaskFragment()
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
         TaskdataBase.getInctace(requireContext()).getTasksDao().DeleteTask(it)
        }


        calender = Calendar.getInstance()
        binding.rvTasks.adapter = adabter
        var item = TaskdataBase.getInctace(requireContext()).getTasksDao().GetTask()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val year = date.year
            val month = date.month - 1
            val day = date.day
            //بهمل التايم علشان بيحسب كل حاجه بال ثواني وبيعمل مشاكل
            deleteTime()
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, day)
           getTask()

        }
        adabter.ubdateTask(item.toMutableList())
        binding.calendarView.selectedDate= CalendarDay.today()

    }

    private fun deleteTime() {
            calender.set(java.util.Calendar.MINUTE,0)
           calender. set(java.util.Calendar.MILLISECOND,0)
            calender.set(java.util.Calendar.SECOND,0)
            calender.set(java.util.Calendar.HOUR_OF_DAY,0)

    }

fun getTask(){
    val ubdatedate = TaskdataBase.getInctace(requireContext()).getTasksDao()
        .GetTaskByDate(calender.time)
    adabter.ubdateTask(ubdatedate.toMutableList())
}


}
