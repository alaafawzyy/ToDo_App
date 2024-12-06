package com.example.todo.com.example.todo_app.database.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date


@Dao
interface TaskDao {
    @Insert
    fun insertTask(tasks: Tasks)

    @Update
    fun UpdateTask(tasks: Tasks)

    @Delete
    fun DeleteTask(tasks: Tasks)

    @Query("SELECT * FROM Tasks")
    fun GetTask():List<Tasks>

    @Query("SELECT * FROM Tasks WHERE date=:date")
    fun GetTaskByDate(date: Date):List<Tasks>
}