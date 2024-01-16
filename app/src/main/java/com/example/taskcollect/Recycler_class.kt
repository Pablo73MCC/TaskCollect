package com.example.taskcollect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcollect.databinding.ItemRecyclerBinding

class Recycler_class(private val notas: List<Nota>) : RecyclerView.Adapter<Recycler_class.NotaViewHolder>() {

    class NotaViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    data class Nota(val titulo: String, val descripcion: String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.binding.itemTitle.text = nota.titulo
        holder.binding.itemDescription.text = nota.descripcion
    }

    override fun getItemCount() = notas.size
}
