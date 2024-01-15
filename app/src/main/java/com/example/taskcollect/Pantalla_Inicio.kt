package com.example.taskcollect

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskcollect.ui.theme.Pantalla_Nota
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Pantalla_Inicio : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        val addButton: FloatingActionButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, Pantalla_Nota::class.java)
            startActivity(intent)
        }
    }
}