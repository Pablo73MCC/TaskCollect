package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.view.MotionEvent
import com.example.taskcollect.ui.theme.Pantalla_Nota


class Pantalla_Inicio : AppCompatActivity() {


    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var adapter: Recycler_class
    private lateinit var searchEditText: EditText
    private var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        dbHelper = DatabaseHelper(this)
        val taskList: MutableList<Nota> = dbHelper?.getAllNotas() ?: mutableListOf()


        tasksRecyclerView = findViewById(R.id.tasks_recyclerview)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Recycler_class(this, taskList, { nota ->
            // Implementación del clic en cada nota
            val intent = Intent(this, Pantalla_Nota::class.java).apply {
                putExtra("id", nota.id.toString())
                putExtra("titulo", nota.titulo)
                putExtra("descripcion", nota.descripcion)
                putExtra("fecha", nota.fecha)
                putExtra("hora", nota.hora)
                putExtra("orden", nota.orden)
                putExtra("evento", nota.evento)
                putExtra("icono", nota.icono)
                putExtra("notificacion", nota.notificacion)
                putExtra("color", nota.color)
                putExtra("recurrencia", nota.recurrencia)
                putExtra("intervalo", nota.intervalo)
                putExtra("finIntervalo", nota.finIntervalo)
            }
            startActivity(intent)
        }, { nota ->
            dbHelper?.deleteNota(nota.id ?: return@Recycler_class)
            actualizarListaDeNotas()
        })

        tasksRecyclerView.adapter = adapter

        configurarInteraccionesUI()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaDeNotas()
    }

    private fun actualizarListaDeNotas() {
        val notas = dbHelper?.getAllNotas() ?: mutableListOf()
        adapter.notas = notas
        adapter.notifyDataSetChanged()
    }

    private fun configurarInteraccionesUI() {
        // Configura aquí el resto de tu UI, como botones flotantes y el buscador
        val addButton: FloatingActionButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, Pantalla_Nota::class.java)
            startActivity(intent)
        }

        // Implementa aquí los demás botones y funciones
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}