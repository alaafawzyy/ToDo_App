package com.example.todo_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.com.example.todo_app.ui.other.ConvertDateType
import com.example.todo_app.database.model.TaskDao
import com.example.todo_app.database.model.Tasks

@Database(entities = [Tasks::class], version = 4)
@TypeConverters(value=[ConvertDateType::class])
  abstract class TaskdataBase :RoomDatabase(){
    abstract fun getTasksDao():TaskDao

    companion object{
      private var INSTANCE:TaskdataBase?= null
      private var DATABASE_NAME="TASKS DATABASE"


      fun getInctace():TaskdataBase{
        return INSTANCE!!
      }

      fun init(context: Context){
        if(INSTANCE == null){
          INSTANCE=Room.databaseBuilder(context,TaskdataBase::class.java, DATABASE_NAME)

            .fallbackToDestructiveMigrationFrom(2)
            .allowMainThreadQueries()
            .build()
        }
      }


    }
}