package fragment
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.todo.R
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.com.example.todo_app.ui.other.OnTaskAddedListener
import com.example.todo_app.database.TaskdataBase
import com.example.todo_app.database.model.Tasks

import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddBottomSheetFragment:BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var calendar: Calendar
    var onTaskAddedListener: OnTaskAddedListener?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()

        binding.selectTimeTil.setOnClickListener({
            addTimePicker()
        })


        binding.selectDateTil.setOnClickListener({
            addDatePicker()


        })




        binding.addTaskBtn.setOnClickListener {
            if (ValidatField()) {
                   deleteTime()
                val task = Tasks(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    date = calendar.time,
                    isDone = false
                )
                TaskdataBase.getInctace(requireContext()).getTasksDao().insertTask(task)
                onTaskAddedListener?.OnTaskAdded()
                dismiss()

            }
        }}
    private fun deleteTime(){
        calendar.set(java.util.Calendar.MINUTE,0)
       calendar. set(java.util.Calendar.MILLISECOND,0)
        calendar.set(java.util.Calendar.SECOND,0)
        calendar.set(java.util.Calendar.HOUR_OF_DAY,0)
    }




    private fun addDatePicker() {

        val picker = DatePickerDialog(
            requireContext(),

            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

                    calendar.set(Calendar.YEAR, p1)
                    calendar.set(Calendar.MONTH, p2)
                    calendar.set(Calendar.DAY_OF_MONTH, p3)
                    binding.selectDateTv.text = "$p1 /${p2 + 1}/ $p3"
                }
            }, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_YEAR)
        )
        picker.show()
    }

    private fun addTimePicker() {
        val picker = TimePickerDialog(
            requireContext(),
            object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                    calendar.set(Calendar.HOUR_OF_DAY, p1)
                    calendar.set(Calendar.MINUTE, p2)
                    binding.selectTimeTv.text = "$p1:$p2"
                }

            }, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        picker.show()
    }

    fun ValidatField(): Boolean {

        if (binding.title.text?.isEmpty() == true || binding.title.text?.isBlank() == true) {
            binding.title.error = "Required"
            return false
        } else binding.title.error = null


        if (binding.description.text?.isEmpty() == true || binding.description.text?.isBlank() == true) {
            binding.description.error = "Required"
            return false
        } else binding.description.error = null


        if (binding.selectTimeTv.text == getString(R.string.select_time))
            return false

        if (binding.selectDateTv.text == getString(R.string.select_date))
            return false



        return true


}
}




