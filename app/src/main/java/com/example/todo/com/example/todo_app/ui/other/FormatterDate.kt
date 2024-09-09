package com.example.todo.com.example.todo_app.ui.other

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FormatterDate {
    fun Date.getDateOnly():String{
        val formater=SimpleDateFormat("dd/ MM/ yyyy",Locale.US)
        return formater.format(this)
    }
    fun Date.getTimeOnly():String{
        val formater=SimpleDateFormat("hh/ mm",Locale.US)
        return formater.format(this)
    }
}