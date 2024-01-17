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
            val titulo = tituloEditText.text.toString()
            val contenido = contenidoEditText.text.toString()
            val id = generateUniqueId()
            guardarNota(id, titulo, contenido)
            finish()
        }
    }

    private fun guardarNota(id: String, titulo: String, contenido: String) {
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val nota = "$id#$titulo#$contenido"
        val totalNotas = sharedPreferences.getInt("totalNotas", 0)
        with(sharedPreferences.edit()) {
            putString("nota_$totalNotas", nota)
            putInt("totalNotas", totalNotas + 1)
            apply()
        }
    }
    private fun obtenerNotas(): List<Recycler_class.Nota> {
        val notas = mutableListOf<Recycler_class.Nota>()
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val totalNotas = sharedPreferences.getInt("totalNotas", 0)
        for (i in 0 until totalNotas) {
            sharedPreferences.getString("nota_$i", null)?.let { nota ->
                val partes = nota.split("#")
                if (partes.size >= 3) {

                    notas.add(Recycler_class.Nota(partes[0], partes[1], partes[2]))
                }
            }
        }
        return notas
    }


    private fun generateUniqueId(): String {
        return System.currentTimeMillis().toString()
    }


}