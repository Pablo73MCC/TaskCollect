package com.example.taskcollect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcollect.databinding.ItemRecyclerBinding

class Recycler_class(private var todasLasNotas: List<Nota>, private val clickListener: (Nota) -> Unit) : RecyclerView.Adapter<Recycler_class.NotaViewHolder>() {

    var notas: List<Nota> = todasLasNotas
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun filter(query: String) {
        notas = if (query.isEmpty()) {
            todasLasNotas
        } else {
            todasLasNotas.filter {
                it.titulo.contains(query, ignoreCase = true) || it.descripcion.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    class NotaViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    data class Nota(val id: String,val titulo: String, val descripcion: String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.binding.itemTitle.text = nota.titulo
        holder.binding.itemDescription.text = nota.descripcion
        holder.itemView.setOnClickListener {
            clickListener(nota)
        }
    }


    override fun getItemCount() = notas.size
}
