package com.example.taskcollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcollect.ui.theme.Pantalla_Nota
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class Pantalla_Inicio : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var adapter: Recycler_class
    private lateinit var searchEditText: EditText
    private var allNotes: List<Recycler_class.Nota> = listOf()
    private var colorFiltroActual: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicio)

        val editText = findViewById<EditText>(R.id.et_search)
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        }

            val addButton: FloatingActionButton = findViewById(R.id.add_button)
            addButton.setOnClickListener {
                val intent = Intent(this, Pantalla_Nota::class.java)
                startActivity(intent)
            }

            val taskList = cargarNotas()
            tasksRecyclerView = findViewById(R.id.tasks_recyclerview)
            allNotes = cargarNotas()
            tasksRecyclerView.layoutManager = LinearLayoutManager(this)
            adapter = Recycler_class(taskList, { nota ->
                // Implementación del clic en cada nota
                val intent = Intent(this, Pantalla_Nota::class.java).apply {
                    putExtra("id", nota.id)
                    putExtra("titulo", nota.titulo)
                    putExtra("contenido", nota.descripcion)
                }
                startActivity(intent)
            }, { notaId ->
                eliminarNotaDeSharedPreferences(notaId)
            })

        tasksRecyclerView.adapter = adapter

        searchEditText = findViewById(R.id.et_search)
        allNotes = cargarNotas()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filterNotes(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        // Boton del menu de los menus
        val btnCalendario: FloatingActionButton = findViewById(R.id.btn_calendario_mn)
        val btnFiltro: FloatingActionButton = findViewById(R.id.btn_filtro_mn)
        val btnTools: FloatingActionButton = findViewById(R.id.btn_tools)

        // Configura un listener para btn_tools
        btnTools.setOnClickListener {
            // Verifica si los botones están visibles o no
            val newVisibility = if (btnCalendario.visibility == View.GONE) View.VISIBLE else View.GONE

            // Cambia la visibilidad de btn_calendario_mn y btn_filtro_mn
            btnCalendario.visibility = newVisibility
            btnFiltro.visibility = newVisibility
        }
        // Función para animar la aparición del botón
        fun showFab(fab: FloatingActionButton) {
            fab.scaleX = 0f
            fab.scaleY = 0f
            fab.visibility = View.VISIBLE
            fab.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
        }

        fun hideFab(fab: FloatingActionButton) {
            fab.animate().scaleX(0f).scaleY(0f).setDuration(200).withEndAction {
                fab.visibility = View.GONE
            }.start()
        }

        btnTools.setOnClickListener {
            if (btnCalendario.visibility == View.GONE) {
                showFab(btnCalendario)
                showFab(btnFiltro)
            } else {
                hideFab(btnCalendario)
                hideFab(btnFiltro)
            }
        }
        // boton del filtro
        btnFiltro.setOnClickListener {
            mostrarSeleccionColor(this)
        }


    }

        override fun onResume() {
            super.onResume()
            Log.d("Pantalla_Inicio", "onResume llamado")
            allNotes = cargarNotas()
            adapter.notas = allNotes
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
                    val colorResId = sharedPreferences.getInt("color_${partes[0]}", R.color.RVF2)
                    notas.add(Recycler_class.Nota(partes[0], partes[1], partes[2],colorResId))
                    Log.d("Pantalla_Inicio", "Nota cargada: ${partes[1]}")
                }
            }
        }
        return notas
    }

    private fun filterNotes(query: String) {
        val filteredNotes = if (query.isEmpty()) {
            allNotes
        } else {
            allNotes.filter {
                it.titulo.contains(query, ignoreCase = true) ||

                        it.descripcion.contains(query, ignoreCase = true)
            }
        }
        adapter.notas = filteredNotes
        Log.d("Pantalla_Inicio", "Entro al filtro")
        adapter.notifyDataSetChanged()
    }

    fun eliminarNotaDeSharedPreferences(idNota: String) {
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("nota_$idNota")
        editor.apply()

        actualizarListaDeNotas()
    }

    // Guardamos color de la nota
    private fun guardarColorNota(notaId: String, colorResId: Int) {
        val sharedPreferences = getSharedPreferences("MisNotas", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("color_$notaId", colorResId)
            apply()
        }
    }

    private fun actualizarListaDeNotas() {
        allNotes = cargarNotas()
        adapter.notas = allNotes
        adapter.notifyDataSetChanged()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    // filtrar notas por color
    private fun filtrarNotasPorColor(colorResId: Int) {
        val notasFiltradasPorColor = if (colorFiltroActual == colorResId) {
            colorFiltroActual = null
            allNotes
        } else {
            colorFiltroActual = colorResId
            allNotes.filter { it.colorResID == colorResId }
        }
        adapter.notas = notasFiltradasPorColor
        adapter.notifyDataSetChanged()
    }

    fun mostrarSeleccionColor(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.color_select, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        val colorChoices = listOf(
            R.id.colorChoice1 to R.color.RVF1,
            R.id.colorChoice2 to R.color.RVF2,
            R.id.colorChoice3 to R.color.RVF3,
            R.id.colorChoice4 to R.color.RVF4,
            R.id.colorChoice5 to R.color.RVF5
        )

        for ((viewId, colorResId) in colorChoices) {
            dialogView.findViewById<View>(viewId).setOnClickListener {
                filtrarNotasPorColor(colorResId)
                dialog.dismiss()
            }
        }
        dialog.show()
    }



}