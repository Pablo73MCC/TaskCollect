package com.example.taskcollect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerClass_calendario(
    private val timeSlots: List<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<RecyclerClass_calendario.TimeSlotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_class_calendario, parent, false)
        return TimeSlotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.timeTextView.text = timeSlot
        holder.itemView.setOnClickListener { onItemClicked(timeSlot) }
    }

    override fun getItemCount() = timeSlots.size

    class TimeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
    }
}