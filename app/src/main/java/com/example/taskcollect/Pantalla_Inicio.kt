package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcollect.ui.theme.Pantalla_Nota
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Pantalla_Inicio : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        val addButton: FloatingActionButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, Pantalla_Nota::class.java)
            startActivity(intent)
        }
        val taskList = cargarNotas() // Carga las notas almacenadas
        val tasksRecyclerView = findViewById<RecyclerView>(R.id.tasks_recyclerview)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = Recycler_class(taskList)
    }

    private fun cargarNotas(): List<Recycler_class.Nota> {
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val notasJson = sharedPreferences.getString("notas", null)
        val gson = Gson()

        return if (!notasJson.isNullOrEmpty()) {
            val type: Type = object : TypeToken<List<Recycler_class.Nota>>() {}.type
            gson.fromJson(notasJson, type)
        } else {
            emptyList()
        }
    }
}