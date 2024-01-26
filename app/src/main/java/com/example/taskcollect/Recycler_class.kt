package com.example.taskcollect

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.example.taskcollect.databinding.ItemRecyclerBinding

class Recycler_class(
    private var todasLasNotas: List<Nota>,
    private val clickListener: (Nota) -> Unit,
    private val eliminarNotaListener: (String) -> Unit
) : RecyclerView.Adapter<Recycler_class.NotaViewHolder>() {
    private val notaColorMapa = mutableMapOf<String, Int>()

    // Definimos los colores que ya tenemos para esta madre
    private val colors = intArrayOf(
        R.color.RVF1,
        R.color.RVF2,
        R.color.RVF3,
        R.color.RVF4,
        R.color.RVF5
    )

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

    // Aqui tenemos todas las madres que se guardan xd
    data class Nota(val id: String,val titulo: String, val descripcion: String, var colorResID: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.binding.itemTitle.text = nota.titulo
        holder.binding.itemDescription.text = nota.descripcion
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, nota.colorResID))
        holder.itemView.setOnClickListener {
            clickListener(nota)
        }

        // Boton de evento al eliminar
        holder.binding.btnEliminar.setOnClickListener {
            mostrarDialogoConfirmacion(holder.itemView.context, position)
        }
    }

    fun mostrarDialogoConfirmacion(context: Context, posicion: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que quieres eliminar esta tarea?")

        builder.setPositiveButton("Eliminar") { dialog, which ->
            // Comprueba que el índice sigue siendo válido
            if (posicion < notas.size) {
                val notaId = notas[posicion].id
                eliminarNota(posicion)
                eliminarNotaListener(notaId)
            }
        }

        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun eliminarNota(posicion: Int) {
        (notas as MutableList).removeAt(posicion)
        notifyItemRemoved(posicion)
    }


    override fun getItemCount() = notas.size
}
