package com.example.todo.com.example.todo_app.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Tasks(
    @PrimaryKey
    val id:Int?=null,
    val description:String?=null,
    val title:String?=null,
    val date: java.util.Date?=null,
    val isDone:Boolean?=false

):Parcelable
