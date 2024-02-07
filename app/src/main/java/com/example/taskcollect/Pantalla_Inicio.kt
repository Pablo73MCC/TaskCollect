package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.taskcollect.Clases.Color_Select
import com.example.taskcollect.databinding.ActivityPantallaInicioBinding
import com.example.taskcollect.databinding.ColorSelectBinding
import com.example.taskcollect.ui.theme.Pantalla_Nota


class Pantalla_Inicio : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaInicioBinding
    private lateinit var adapter: Recycler_class
    private var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaInicioBinding.inflate(layoutInflater)

        // Esta madre lo que hace es utilizar la vista inflada y configurada a través del View Binding
        setContentView(binding.root)
//        setContentView(R.layout.activity_pantalla_inicio)

        dbHelper = DatabaseHelper(this)
        val taskList: MutableList<Nota> = dbHelper?.getAllNotas() ?: mutableListOf()

        // Cambia
        binding.tasksRecyclerview.layoutManager = LinearLayoutManager(this)
//        tasksRecyclerView = findViewById(R.id.tasks_recyclerview)
//        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
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

        binding.tasksRecyclerview.adapter = adapter

        configurarInteraccionesUI()
        botonesExtra()
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
            binding.addButton.setOnClickListener{
            val intent = Intent(this, Pantalla_Nota::class.java)
            startActivity(intent)
        }
    }
    private fun botonesExtra() {
        val btnMain = binding.btnTools
        val btnCalendario = binding.btnCalendarioMn
        val btnFiltro = binding.btnFiltroMn
//        val btnMain: FloatingActionButton = findViewById(R.id.btn_tools)
//        val btnCalendario: FloatingActionButton = findViewById(R.id.btn_calendario_mn)
//        val btnFiltro: FloatingActionButton = findViewById(R.id.btn_filtro_mn)

        // Listener para el botón principal que muestra u oculta los otros botones
        btnMain.setOnClickListener {
            if (btnCalendario.visibility == View.GONE) {
                btnCalendario.show()
                btnFiltro.show()
            } else {
                btnCalendario.hide()
                btnFiltro.hide()
            }
        }
        // Listener para el botón del calendario
        btnCalendario.setOnClickListener {
            val intent = Intent(this, Calendario::class.java)
            startActivity(intent)
        }
        // Listener para el botón del filtro
        btnFiltro.setOnClickListener {
            val dialog = Color_Select()
            dialog.show(supportFragmentManager, "colorSelect")
        }
    }


    // Esta mamada de cerrar el teclado de afuerzas
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}




































































// Es la primera vez
// Que invito a alguien desde que te fuiste
// Y estoy bien
// El mismo restaurante
// Pero a ella sí le dan risa mis chistes
// Y estoy bien
// Ella sí se lleva bien con mis amigos
// Nunca discutimos
// Es lo que siempre he querido
// Pero cuando la miro a los ojos
// Veo que no son
// Tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// El cielo azul parece gris
// Desde que ya no estás aquí
// Todo el color ahora es blanco y negro
// Y como no te puedo hablar
// Ni regresar el tiempo atrás
// Mejor al Sol lo tapo con un dedo
// Sus labios lucen de rojo
// Nos vemos bien de la mano
// Y me gusta el verde en sus ojos
// Si no los comparo
// Con tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Oh-oh-oh
// Sin tus ojos marrones
// Ah-ah
// Ella sí se lleva bien con mis amigos
// Nunca discutimos
// Es lo que siempre he querido
// Pero cuando la miro a los ojos
// Veo que no son
// Tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Nada es igual, nada es igual, nada
// Sin tus ojos marrones
// Nada, nada es igual
// (Nada es igual, nada es igual, nada es igual) nada es igual
// Nada es igual sin tus ojos marrones
// (Nada es igual, nada es igual, nada es igual) sin tus ojos marrones
// Ayayay, sin tus ojos marrones
// (Nada es igual, nada es igual, nada es igual)