package com.example.todo.com.example.todo_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo_app.database.model.Tasks

import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private var taskList: MutableList<Tasks>?):RecyclerView.Adapter<TaskAdapter.TasksViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding= ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TasksViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList?.size?:0
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
       var item=taskList?.get(position)?:return
        holder.bind(item)
        holder.binding.editTextSwipe.setOnClickListener({
            onTaskClickListener?.OnTaskClick(item)

        })
        holder.binding.imageForDelete.setOnClickListener({
            onTaskClickListener?.OnTaskClick(item)
            taskList!!.remove(item)
            notifyItemRemoved(position)

        })
    }
    fun ubdateTask(task:MutableList<Tasks>){
        this.taskList=task
        notifyDataSetChanged()
    }
    private var onTaskClickListener: OnTaskClickListener?=null
//علشان احقق ال encapsoluation بمعني ان ال الاوبجكت برايفت وبوصل بس عن طريق الفانكشن
    fun setOnTaskClickListener(listener: OnTaskClickListener){
        onTaskClickListener=listener
    }

    fun interface OnTaskClickListener{
        fun OnTaskClick(tasks: Tasks)

    }

    class TasksViewHolder(val binding: ItemTaskBinding):ViewHolder(binding.root){
        fun bind(tasks: Tasks){
            binding.task=tasks
            binding.invalidateAll()
            binding.title.text=tasks.title
            val simpleDateFormat=SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            val dateAsString=simpleDateFormat.format(tasks.date)
            binding.time.text=dateAsString
        }

    }

}