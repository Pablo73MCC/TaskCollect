package com.example.taskcollect.Clases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.taskcollect.databinding.ColorSelectBinding

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