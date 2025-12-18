package com.example.a18122025.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.a18122025.R
import com.example.a18122025.data.DatabaseHelper
import com.example.a18122025.model.Task

class TaskAdapter(
    private val context: Context,
    private val tasks: ArrayList<Task>,
    private val db: DatabaseHelper
) : BaseAdapter() {

    override fun getCount() = tasks.size
    override fun getItem(position: Int) = tasks[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_task, parent, false)

        val task = tasks[position]

        val cb: CheckBox = view.findViewById(R.id.cbStatus)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val date: TextView = view.findViewById(R.id.tvDate)
        val desc: TextView = view.findViewById(R.id.tvDesc)

        title.text = task.title
        date.text = task.date
        desc.text = task.description
        cb.isChecked = task.status == 1

        title.paintFlags =
            if (task.status == 1)
                title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        cb.setOnCheckedChangeListener { _, checked ->
            task.status = if (checked) 1 else 0
//            db.updateTask(task.id, task.title, task.description, task.date, task.status)
            db.updateTask(
                task.id,
                task.title ?: "",
                task.description ?: "",
                task.date ?: "",
                task.status
            )

            notifyDataSetChanged()
        }

        view.findViewById<ImageButton>(R.id.btnEdit).setOnClickListener {
            val i = Intent(context, AddEditTaskActivity::class.java)
            i.putExtra("isEdit", true)
            i.putExtra("taskId", task.id)
            i.putExtra("userId", task.userId)
            context.startActivity(i)
        }

        view.findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Xóa?")
                .setPositiveButton("Xóa") { _, _ ->
                    db.deleteTask(task.id)
                    tasks.removeAt(position)
                    notifyDataSetChanged()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        return view
    }
}
