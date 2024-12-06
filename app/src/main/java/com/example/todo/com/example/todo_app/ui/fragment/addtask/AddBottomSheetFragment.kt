package com.example.todo.com.example.todo_app.ui.fragment.addtask
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todo.com.example.todo_app.ui.other.OnTaskAddedListener
import com.example.todo.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddBottomSheetFragment : BottomSheetDialogFragment() {
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
    ): View {
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
        binding.lifecycleOwner = this

        binding.selectTimeTil.setOnClickListener { addTimePicker() }
        binding.selectDateTil.setOnClickListener { addDatePicker() }
    }

    private fun observeViewModel() {
        viewModel.isDoneLiveData.observe(viewLifecycleOwner) { isDone ->
            if (isDone) {
                onTaskAddedListener?.OnTaskAdded()
                dismiss()
            }
        }
    }

    private fun addDatePicker() {
        val picker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                viewModel.calendar.set(Calendar.YEAR, year)
                viewModel.calendar.set(Calendar.MONTH, month)
                viewModel.calendar.set(Calendar.DAY_OF_MONTH, day)
                binding.selectDateTv.text = "$year/${month + 1}/$day"
            },
            viewModel.calendar.get(Calendar.YEAR),
            viewModel.calendar.get(Calendar.MONTH),
            viewModel.calendar.get(Calendar.DAY_OF_MONTH)
        )
        picker.show()
    }

    private fun addTimePicker() {
        val picker = TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                viewModel.calendar.set(Calendar.HOUR_OF_DAY, hour)
                viewModel.calendar.set(Calendar.MINUTE, minute)
                binding.selectTimeTv.text = "$hour:$minute"
            },
            viewModel.calendar.get(Calendar.HOUR_OF_DAY),
            viewModel.calendar.get(Calendar.MINUTE),
            false
        )
        picker.show()
    }
}
