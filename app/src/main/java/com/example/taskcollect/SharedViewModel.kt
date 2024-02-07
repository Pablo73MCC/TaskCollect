package com.example.taskcollect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SharedViewModel : ViewModel(){
    private val _selectedColor = MutableLiveData<String>()
    val selectColorSelectBinding = _selectedColor
    fun selectColor(color:String){
        _selectedColor.value = color
    }
}