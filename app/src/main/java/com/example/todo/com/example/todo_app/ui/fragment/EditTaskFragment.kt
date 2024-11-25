package fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.todo.databinding.TaskDetailsActivityBinding
import com.example.todo.com.example.todo_app.ui.other.Consstant
import com.example.todo.com.example.todo_app.ui.other.FormatterDate.getDateOnly
import com.example.todo.com.example.todo_app.ui.other.FormatterDate.getTimeOnly
import com.example.todo_app.database.TaskdataBase
import com.example.todo_app.database.model.Tasks


class EditTaskFragment:Fragment() {
    lateinit var binding: TaskDetailsActivityBinding
    lateinit var tasks: Tasks
    lateinit var calender:Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=TaskDetailsActivityBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calender=Calendar.getInstance()
        getPassetTask()
        bindTask(tasks)
        binding.content.selectDateTv.setOnClickListener({addDatePicker()})
        binding.content.selectTimeTv.setOnClickListener({addTimePicker()})
        binding.content.saveBtn.setOnClickListener({saveTask()})
    }


    private fun getPassetTask() {
        arguments.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                tasks= requireArguments().getParcelable(Consstant.PassedTask,Tasks::class.java)?:Tasks()
            }
            else
                tasks= requireArguments().getParcelable(Consstant.PassedTask)?:Tasks()
        }
    }
    private fun bindTask(tasks: Tasks) {
         binding.content.title.setText(tasks.title)
        binding.content.description.setText(tasks.description)
        binding.content.selectDateTv.setText(tasks.date?.getDateOnly())
        binding.content.selectTimeTv.setText(tasks.date?.getTimeOnly())

    }
    private fun saveTask(){
        val newTask=Tasks(
            tasks.id,
            title = binding.content.title.text.toString(),
            description = binding.content.description.toString(),
            date = calender.time
        )
        TaskdataBase.getInctace().getTasksDao()
            .UpdateTask(newTask)
    }
    private fun addDatePicker() {
        val picker = DatePickerDialog(
            requireContext(),

            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

                    calender.set(Calendar.YEAR, p1)
                    calender.set(Calendar.MONTH, p2)
                    calender.set(Calendar.DAY_OF_MONTH, p3)
                    binding.content.selectTimeTv.text = "$p1 /${p2 + 1}/ $p3"
                }
            }, calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_YEAR)
        )
        picker.show()
    }

    private fun addTimePicker() {
        val picker = TimePickerDialog(
            requireContext(),
            object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                    calender.set(Calendar.HOUR_OF_DAY, p1)
                    calender.set(Calendar.MINUTE, p2)
                    binding.content.selectDateTv.text = "$p1:$p2"
                }

            }, calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            false
        )
        picker.show()
    }


}