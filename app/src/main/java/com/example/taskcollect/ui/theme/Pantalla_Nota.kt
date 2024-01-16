package com.example.taskcollect.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.taskcollect.R
import android.content.Context
import android.util.Log
import android.widget.EditText
import com.example.taskcollect.Recycler_class
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Pantalla_Nota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_nota)

        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }

        val tituloEditText: EditText = findViewById(R.id.txt_titulo)
        val contenidoEditText: EditText = findViewById(R.id.txt_nota)

        val saveButton: Button = findViewById(R.id.btn_save)
        saveButton.setOnClickListener {
            Log.d("Pantalla_Nota", "Botón de guardar presionado")
            val titulo = tituloEditText.text.toString()
            val contenido = contenidoEditText.text.toString()
            guardarNota(titulo, contenido)
        }
    }

    private fun guardarNota(titulo: String, contenido: String) {
        Log.d("Pantalla_Nota", "Guardando nota: Título - $titulo, Contenido - $contenido")
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val notasExistentesJson = sharedPreferences.getString("notas", "")
        val gson = Gson()

        // Lista para guardar notas existentes más la nueva
        val notas: MutableList<Recycler_class.Nota> = if (!notasExistentesJson.isNullOrEmpty()) {
            val type: Type = object : TypeToken<MutableList<Recycler_class.Nota>>() {}.type
            gson.fromJson(notasExistentesJson, type)
        } else {
            mutableListOf()
        }

        notas.add(Recycler_class.Nota(titulo, contenido))

        with(sharedPreferences.edit()) {
            putString("notas", gson.toJson(notas))
            apply()
        }
    }
}