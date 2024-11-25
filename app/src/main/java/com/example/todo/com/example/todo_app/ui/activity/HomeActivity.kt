package com.example.todo.com.example.todo_app.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.com.example.todo_app.ui.fragment.addtask.AddBottomSheetFragment
import com.example.todo.com.example.todo_app.ui.fragment.taskList.TaskFragment
import com.example.todo.com.example.todo_app.ui.other.OnTaskAddedListener

import com.example.todo.databinding.ActivityHomeBinding

import fragment.SettingFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var taskFragment: TaskFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        binding.bottomNavView.selectedItemId = R.id.nav_bar_tasks
        taskFragment = TaskFragment()
        addFragment(taskFragment)

        binding.fabAddTask.setOnClickListener { navigateToFabAddTask() }

        binding.bottomNavView.setOnItemSelectedListener { bottomNavigationView(it) }
    }

    private fun bottomNavigationView(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.nav_bar_settings -> {
                addFragment(SettingFragment())
            }
            R.id.nav_bar_tasks -> {
                if (!::taskFragment.isInitialized) {
                    taskFragment = TaskFragment()
                }
                addFragment(taskFragment)
            }
        }
        return true
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun navigateToFabAddTask() {

        val addTaskBottomSheet = AddBottomSheetFragment()
        addTaskBottomSheet.onTaskAddedListener = object : OnTaskAddedListener {
            override fun OnTaskAdded() {

                if (::taskFragment.isInitialized && taskFragment.isVisible) {
                    taskFragment.getTask()
                }
            }
        }

        addTaskBottomSheet.show(supportFragmentManager, "AddTaskBottomSheet")
    }


}
