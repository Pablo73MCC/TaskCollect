package com.example.taskcollect.Clases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.taskcollect.R
import com.example.taskcollect.SharedViewModel
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
    private lateinit var binding:ColorSelectBinding
    class Color(
        val context: Context,
        val idColor:Int = -1,
        var color:Int = context.getColor(R.color.RVF2),
        val nombreColor:String = ""

    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ColorSelectBinding.inflate(inflater, container, false)
        return binding.root
    }
}

//
//    private lateinit var binding: ColorSelectBinding
//    private val colores = ArrayList<Color >
//    //private lateinit var viewModel: SharedViewModel
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = ColorSelectBinding.inflate(inflater, container, false)
//        setupColorSelection()
//        return binding.root
//    }
//
//    private fun setupColorSelection() {
//        val colorViews = listOf(
//            binding.colorChoice1,
//            binding.colorChoice2,
//            binding.colorChoice3,
//            binding.colorChoice4,
//            binding.colorChoice5
//        )
//
//        colorViews.forEach { imageView ->
//            imageView.setOnClickListener { view ->
//                val selectedColor = view.tag.toString()
//                viewModel.selectColor(selectedColor)
//                dismiss()
//            }
//        }
//    }
