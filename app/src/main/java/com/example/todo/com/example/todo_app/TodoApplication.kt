package com.example.todo.com.example.todo_app

import android.app.Application
import com.example.todo_app.database.TaskdataBase

class TodoApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        TaskdataBase.init(this )
    }
}