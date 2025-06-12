package com.example.monsterhunterarmor.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monsterhunterarmor.presentation.home.ArmorViewModel
import com.example.monsterhunterarmor.repository.ArmorRepository

class ViewModelFactory(private val repository: ArmorRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArmorViewModel::class.java)) {
            return ArmorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}