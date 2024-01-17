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

    private var notaId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_nota)

        // Esta madre es el boton de regresar
        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        // Esta madre es el boton de guardar y te regresa a la pagina de inicio
        val tituloEditText: EditText = findViewById(R.id.txt_titulo)
        val contenidoEditText: EditText = findViewById(R.id.txt_nota)

        // Verificar si estamos editando una nota existente
        notaId = intent.getStringExtra("id") // El ID de la nota si se está editando, o null si es nueva
        if(notaId != null) {
            // Estamos editando una nota existente
            tituloEditText.setText(intent.getStringExtra("titulo"))
            contenidoEditText.setText(intent.getStringExtra("contenido"))
        }

        val saveButton: Button = findViewById(R.id.btn_save)
        saveButton.setOnClickListener {
            val titulo = tituloEditText.text.toString()
            val contenido = contenidoEditText.text.toString()
            val id = notaId ?: generateUniqueId() // Usar notaId si está editando, sino generar uno nuevo
            guardarNota(id, titulo, contenido)
            finish()
        }
    }

    private fun guardarNota(id: String, titulo: String, contenido: String) {
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Si es una nueva nota, actualizamos el contador de totalNotas y usamos ese contador como ID.
        val nuevaId = if (notaId == null) {
            val totalNotas = sharedPreferences.getInt("totalNotas", 0)
            editor.putInt("totalNotas", totalNotas + 1)
            totalNotas.toString()
        } else {
            id
        }
        editor.putString("nota_$nuevaId", "$nuevaId#$titulo#$contenido")
        editor.apply()

        Log.d("Pantalla_Nota", "Nota guardada: $titulo con ID: $nuevaId")
    }


    private fun generateUniqueId(): String {
        return System.currentTimeMillis().toString()
    }


}