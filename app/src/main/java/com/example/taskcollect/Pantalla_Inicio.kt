package com.example.taskcollect

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.preference.PreferenceManager
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

class Pantalla_Inicio : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
//
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)
//
//        if (darkModeIsActive) {
//            setTheme(R.style.Theme_TaskCollect_Dark)
//        } else {
//            setTheme(R.style.Theme_TaskCollect)
//        }
//        setContentView(R.layout.activity_pantalla_inicio)
//
//        // Inicializa los elementos de la interfaz de usuario
//        editTextTask = findViewById(R.id.editTextTask)
//        buttonAddTask = findViewById(R.id.buttonAddTask)
//        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
//        switchDarkMode = findViewById(R.id.switchDarkMode)
//
//
//        switchDarkMode.isChecked = darkModeIsActive
//        // Listener para el cambio de estado del Switch
//        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
//            with(sharedPreferences.edit()) {
//                putBoolean("dark_mode", isChecked)
//                apply()
//            }
//            // Recargar la actividad para aplicar el cambio de tema
//            recreate()
//        }
//
//
//        // Inicializa la lista de tareas
//        recyclerViewTasks = mutableListOf()
//
//        // Crea un adaptador para la ListView
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recyclerViewTasks)
//        listViewTasks.adapter = adapter
//
//        // Agrega una tarea cuando se hace clic en el botón "Agregar Tarea"
//        buttonAddTask.setOnClickListener {
//            val task = editTextTask.text.toString()
//            if (task.isNotEmpty()) {
//                recyclerViewTasks.add(task)
//                adapter.notifyDataSetChanged()
//                editTextTask.text.clear()
//            }
//        }
//
    }
}