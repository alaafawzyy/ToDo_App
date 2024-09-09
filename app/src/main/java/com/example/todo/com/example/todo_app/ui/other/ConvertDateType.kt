package com.example.todo.com.example.todo_app.ui.other

import androidx.room.TypeConverter

class ConvertDateType {
    @TypeConverter
    fun ConvertToDate(datetime:Long):java.util.Date{
      return java.util.Date(datetime)
    }
@TypeConverter
    fun ConvertFromDate(date:java.util.Date):Long{
          return date.time
            }
}

