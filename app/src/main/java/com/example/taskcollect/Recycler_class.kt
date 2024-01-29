package com.example.taskcollect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
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
        holder.binding.itemImage.setOnClickListener {
            mostrarDialogoSeleccionColor(holder.itemView.context, nota)
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

    // Poder cambiar de color la nota
    fun mostrarDialogoSeleccionColor(context: Context, nota: Recycler_class.Nota) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.color_select, null)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        // Configura los eventos onClick para cada vista de color
        dialogView.findViewById<View>(R.id.colorChoice1).setOnClickListener {
            actualizarColorNota(context, nota, R.color.RVF1)
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.colorChoice2).setOnClickListener {
            actualizarColorNota(context, nota, R.color.RVF2)
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.colorChoice3).setOnClickListener {
            actualizarColorNota(context, nota, R.color.RVF3)
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.colorChoice4).setOnClickListener {
            actualizarColorNota(context, nota, R.color.RVF4)
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.colorChoice5).setOnClickListener {
            actualizarColorNota(context, nota, R.color.RVF5)
            dialog.dismiss()
        }

        dialog.show()
    }

    fun actualizarColorNota(context: Context, nota: Recycler_class.Nota, @ColorRes colorResId: Int) {
        val position = notas.indexOf(nota)
        if (position != -1) {
            nota.colorResID = colorResId // Aquí se actualiza la propiedad de la nota
            notifyItemChanged(position)
            guardarColorNota(context, nota.id, colorResId) // Aquí se guarda el color en el SharedPreferences
        }
    }

    private fun guardarColorNota(context: Context, notaId: String, colorResId: Int) {
        val sharedPreferences = context.getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("color_$notaId", colorResId)
            apply()
        }
    }


    override fun getItemCount() = notas.size
}
