package com.example.todo.com.example.todo_app.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.com.example.todo_app.ui.other.OnTaskAddedListener
import com.example.todo.databinding.ActivityHomeBinding
import fragment.AddBottomSheetFragment
import fragment.SettingFragment
import fragment.TaskFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var taskFragment: TaskFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.fabAddTask.setOnClickListener({

                val addTaskbottomsheet=AddBottomSheetFragment()
                addTaskbottomsheet.onTaskAddedListener=object : OnTaskAddedListener {
                    override fun OnTaskAdded() {
                        taskFragment?.getTask()
                    }
                }
                addTaskbottomsheet.show(supportFragmentManager, null)
            })
        binding.bottomNavView.setOnItemSelectedListener {
              when(it.itemId){
                R.id.nav_bar_settings ->{
                       addFragment(SettingFragment())
                }
                R.id.nav_bar_tasks ->{
                    taskFragment=TaskFragment()
                    addFragment(taskFragment)
                }}
                 return@setOnItemSelectedListener true
        }
        binding.bottomNavView.selectedItemId=R.id.nav_bar_tasks



    }
    private fun addFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment)
            .commit()
    }
}