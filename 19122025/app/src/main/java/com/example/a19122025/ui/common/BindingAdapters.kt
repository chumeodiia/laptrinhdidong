package com.example.a19122025.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("app:dateFormat")
fun TextView.setDateFormat(timestamp: Long?) {
    if (timestamp != null) {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = Date(timestamp)
        text = dateFormat.format(date)
    } else {
        text = ""
    }
}

@BindingAdapter("app:priorityColor")
fun TextView.setPriorityColor(priority: Int) {
    val color = when (priority) {
        3 -> context.getColor(android.R.color.holo_red_dark)
        2 -> context.getColor(android.R.color.holo_orange_dark)
        else -> context.getColor(android.R.color.holo_green_dark)
    }
    setTextColor(color)
}