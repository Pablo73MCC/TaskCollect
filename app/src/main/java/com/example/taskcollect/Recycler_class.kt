package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.example.taskcollect.Clases.Color_Change
import com.example.taskcollect.databinding.ItemRecyclerBinding

class Recycler_class(
    private val context: Context,
    private var todasLasNotas: MutableList<Nota>,
    private val clickListener: (Nota) -> Unit,
    private val eliminarNotaListener: (Nota) -> Unit // Cambiado para usar la nota en lugar de solo el ID
) : RecyclerView.Adapter<Recycler_class.NotaViewHolder>() {

    private val dbHelper = DatabaseHelper(context)

    var notas: List<Nota> = todasLasNotas
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        loadNotasFromDatabase()
    }

    private fun loadNotasFromDatabase() {
        todasLasNotas.clear()
        todasLasNotas.addAll(dbHelper.getAllNotas())
        notas = todasLasNotas
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.binding.itemTitle.text = nota.titulo
        holder.binding.itemDescription.text = nota.descripcion
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.RVF2))

        // Manejador de clics para el itemView
        holder.itemView.setOnClickListener {
            clickListener(nota)
        }

        // Manejador de clics para el botón de editar, separado del itemView
        holder.binding.itemImage.setOnClickListener {
            val colorChangeDialog = Color_Change()
            colorChangeDialog.show((context as AppCompatActivity).supportFragmentManager, "colorChange")
        }
    }

    private fun mostrarDialogoConfirmacion(context: Context, nota: Nota) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que quieres eliminar esta nota?")

        builder.setPositiveButton("Eliminar") { dialog, which ->
            dbHelper.deleteNota(nota.id ?: return@setPositiveButton)
            eliminarNotaListener(nota)
            loadNotasFromDatabase()
        }

        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }

    override fun getItemCount() = notas.size
}