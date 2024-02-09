package com.example.taskcollect.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.taskcollect.R
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.taskcollect.DatabaseHelper
import com.example.taskcollect.Nota
class Pantalla_Nota : AppCompatActivity() {

    private var notaId: Int? = null // Cambiado a Int, ya que la base de datos usa Int como ID
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_nota)

        dbHelper = DatabaseHelper(this)

        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val tituloEditText: EditText = findViewById(R.id.txt_titulo)
        val contenidoEditText: EditText = findViewById(R.id.txt_nota)

        notaId = intent.getIntExtra("id", -1).takeIf { it != -1 }

        notaId?.let { id ->
            val nota = dbHelper.getNota(id)
            tituloEditText.setText(nota?.titulo)
            contenidoEditText.setText(nota?.descripcion)
        }

        // Aqui guardamos la informacion de la nota a la base
        val saveButton: Button = findViewById(R.id.btn_save)
        saveButton.setOnClickListener {
            val titulo = tituloEditText.text.toString()
            val contenido = contenidoEditText.text.toString()

            if (titulo.isEmpty() || contenido.isEmpty()) {
                mostrarDialogoAlerta()
            } else {
                val nota = Nota(
                    id = notaId,
                    titulo = titulo,
                    descripcion = contenido,
                    // Asumiendo que los campos siguientes pueden ser nulos o tienen valores por defecto
                    fecha = null,
                    hora = null,
                    orden = null,
                    evento = null,
                    icono = null,
                    notificacion = null,
                    color = "#47AFCA",
                    recurrencia = null,
                    intervalo = null,
                    finIntervalo = null
                )
                if(notaId == null) {
                    dbHelper.addNota(nota) // Insertar nueva nota
                } else {
                    dbHelper.updateNota(nota) // Actualizar nota existente
                }
                finish()
            }
        }
    }

    private fun mostrarDialogoAlerta() {
        AlertDialog.Builder(this)
            .setTitle("Campos Incompletos")
            .setMessage("Por favor, ingresa un título y contenido para la nota.")
            .setPositiveButton("OK", null)
            .show()
    }
}