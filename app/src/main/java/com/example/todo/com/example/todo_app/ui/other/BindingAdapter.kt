package com.example.todo.com.example.todo_app.ui.other

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("error")
fun TextInputEditText.setCustomError(erroeMessage:String?){
    error=erroeMessage
}
@BindingAdapter("formatDate")
fun TextView.formatingDate(date:Date?){
    val simpleDateFormat=SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateAsString=simpleDateFormat.format(date?:Date())
    text=dateAsString
}