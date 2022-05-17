package com.omranic.eyada.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omranic.eyada.R
import com.omranic.eyada.model.Category

class CategoryViewModel : ViewModel() {
    private var categories = MutableLiveData<List<Category>>()

    fun getCategories(): LiveData<List<Category>> {
        categories.value = loadCategories()
        return categories
    }

    private fun loadCategories(): List<Category>{
        val categories = mutableListOf<Category>()
        categories.add(Category("Cardiologist", R.drawable.ic_cardiologist))
        categories.add(Category("Neurosurgery", R.drawable.ic_neurosurgeon))
        categories.add(Category("Pediatrician", R.drawable.ic_pediatrician))
        categories.add(Category("Psychically", R.drawable.ic_psychiatrist))
        return categories
    }
}