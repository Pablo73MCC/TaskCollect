package com.example.taskcollect.Clases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.taskcollect.databinding.ColorSelectBinding

// Esta es la madre del filtro en pantalla de inicio en herramientas
class Color_Select : DialogFragment() {
        private lateinit var binding:ColorSelectBinding
        private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
            binding = ColorSelectBinding.inflate(inflater, container, false)
            return binding.root
        }
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return initBinding(inflater,container)
        }
    }

// Esta es la madre de la paleta de colores en el RecyclerView
class Color_Change : DialogFragment() {
    private lateinit var binding: ColorSelectBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ColorSelectBinding.inflate(inflater, container, false)
        return binding.root
    }
}