package com.example.todo.com.example.todo_app.ui.fragment.addtask
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.com.example.todo_app.ui.other.OnTaskAddedListener
import com.example.todo_app.database.TaskdataBase
import com.example.todo_app.database.model.Tasks

import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddBottomSheetFragment:BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    lateinit var viewModel: AddTaskViewModel
    var onTaskAddedListener: OnTaskAddedListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddTaskViewModel::class.java)
    }

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
        viewModel.calendar = Calendar.getInstance()
        initView()
        observeViewModel()
    }


    private fun initView() {
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this

        binding.selectTimeTil.setOnClickListener({ addTimePicker() })

        binding.selectDateTil.setOnClickListener({ addDatePicker() })
    }

    fun observeViewModel() {
        viewModel.isDoneLiveData.observe(viewLifecycleOwner) {
            if (it) {
                onTaskAddedListener?.OnTaskAdded()
                dismiss()
            }
        }
    }

    private fun deleteTime(){
       viewModel. calendar.set(java.util.Calendar.MINUTE,0)
      viewModel. calendar. set(java.util.Calendar.MILLISECOND,0)
       viewModel.calendar.set(java.util.Calendar.SECOND,0)
        viewModel.calendar.set(java.util.Calendar.HOUR_OF_DAY,0)
    }

    private fun addDatePicker() {
        val picker = DatePickerDialog(
            requireContext(),
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                   viewModel.calendar.set(Calendar.YEAR, p1)
                    viewModel.calendar.set(Calendar.MONTH, p2)
                    viewModel.calendar.set(Calendar.DAY_OF_MONTH, p3)
                    binding.selectDateTv.text = "$p1 /${p2 + 1}/ $p3"
                }
            }, viewModel.calendar.get(Calendar.YEAR),
           viewModel. calendar.get(Calendar.MONTH),
           viewModel. calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun addTimePicker() {
        val picker = TimePickerDialog(
            requireContext(),
            object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                    viewModel.calendar.set(Calendar.HOUR_OF_DAY, p1)
                    viewModel.calendar.set(Calendar.MINUTE, p2)
                    binding.selectTimeTv.text = "$p1:$p2"
                }

            },viewModel. calendar.get(Calendar.HOUR_OF_DAY),
           viewModel. calendar.get(Calendar.MINUTE),
            false
        )
        picker.show()
    }


}




