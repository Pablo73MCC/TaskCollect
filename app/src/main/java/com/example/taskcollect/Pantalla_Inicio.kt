package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcollect.ui.theme.Pantalla_Nota
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Pantalla_Inicio : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var adapter: Recycler_class

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

//    override fun onResume() {
//        super.onResume()
//        val taskList = cargarNotas() // Carga las notas almacenadas
//        val tasksRecyclerView = findViewById<RecyclerView>(R.id.tasks_recyclerview)
//        tasksRecyclerView.layoutManager =
//            LinearLayoutManager(this) // Asegúrate de tener un LayoutManager
//
//        // Configurar el adaptador con el listener de clics
//        tasksRecyclerView.adapter = Recycler_class(taskList) { nota ->
//            // Aquí manejas el clic en una nota
//            val intent = Intent(this, Pantalla_Nota::class.java).apply {
//                putExtra("id", nota.id)
//                putExtra("titulo", nota.titulo)
//                putExtra("contenido", nota.descripcion)
//            }
//            startActivity(intent)
//        }
//    }
}