package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcollect.ui.theme.Pantalla_Nota
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskcollect.databinding.ItemRecyclerBinding

class Pantalla_Inicio : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var adapter: Recycler_class
    private lateinit var searchEditText: EditText
    private var allNotes: List<Recycler_class.Nota> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)


            val addButton: FloatingActionButton = findViewById(R.id.add_button)
            addButton.setOnClickListener {
                val intent = Intent(this, Pantalla_Nota::class.java)
                startActivity(intent)
            }

            val taskList = cargarNotas()
            tasksRecyclerView = findViewById(R.id.tasks_recyclerview)
            allNotes = cargarNotas()
            tasksRecyclerView.layoutManager = LinearLayoutManager(this)
            adapter = Recycler_class(taskList) { nota ->
                // Implementación del clic en cada nota
                val intent = Intent(this, Pantalla_Nota::class.java).apply {
                    putExtra("id", nota.id)
                    putExtra("titulo", nota.titulo)
                    putExtra("contenido", nota.descripcion)
                }
                startActivity(intent)
            }

        tasksRecyclerView.adapter = adapter

        searchEditText = findViewById(R.id.et_search) // Asegúrate de tener este ID en tu XML
        allNotes = cargarNotas()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filterNotes(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        }

        override fun onResume() {
            super.onResume()
            Log.d("Pantalla_Inicio", "onResume llamado")
            val nuevasNotas = cargarNotas()
            adapter.notas = nuevasNotas
            adapter.notifyDataSetChanged()
        }


    private fun cargarNotas(): List<Recycler_class.Nota> {
        val notas = mutableListOf<Recycler_class.Nota>()
        Log.d("Pantalla_Inicio","Entra a cargar notas")
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val totalNotas = sharedPreferences.getInt("totalNotas", 0)
        for (i in 0 until totalNotas) {
            Log.d("Pantalla_Inicio", "Buscando nota con clave: nota_$i")
            val notaString = sharedPreferences.getString("nota_$i", null)
            Log.d("Pantalla_Inicio", "Valor recuperado para nota_$i: $notaString")
            notaString?.let {
                val partes = it.split("#")
                if (partes.size >= 3) {
                    notas.add(Recycler_class.Nota(partes[0], partes[1], partes[2]))
                    Log.d("Pantalla_Inicio", "Nota cargada: ${partes[1]}")
                }
            }
        }
        return notas
    }

    private fun filterNotes(query: String) {
        val filteredNotes = if (query.isEmpty()) {
            allNotes
        } else {
            allNotes.filter {
                it.titulo.contains(query, ignoreCase = true) ||
                        it.descripcion.contains(query, ignoreCase = true)
            }
        }
        adapter.notas = filteredNotes
        adapter.notifyDataSetChanged()
    }


}