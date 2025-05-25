package com.example.wuwalist.utils

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wuwalist.presentation.home.HomeViewModel

class HomeViewModelFactory(private val resources: Resources) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(resources) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}