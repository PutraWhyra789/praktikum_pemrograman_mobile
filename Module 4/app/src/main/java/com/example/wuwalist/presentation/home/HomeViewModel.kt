package com.example.wuwalist.presentation.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wuwalist.R
import com.example.wuwalist.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val resources: Resources) : ViewModel() {

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> get() = _characterList

    private fun getCharacterFlow(): Flow<List<Character>> = flow {

        val dataName = resources.getStringArray(R.array.nama_character)
        val dataLink = resources.getStringArray(R.array.link_character)
        val dataDescription = resources.getStringArray(R.array.deskripsi_character)
        val dataPhoto = resources.obtainTypedArray(R.array.photo_character)

        val listCharacter = ArrayList<Character>()
        for (i in dataName.indices) {
            val chara = Character(dataName[i], dataLink[i], dataDescription[i],dataPhoto.getResourceId(i, -1))
            listCharacter.add(chara)
        }
        dataPhoto.recycle()
        emit(listCharacter)
    }

    fun loadCharacters() {
        viewModelScope.launch {
            getCharacterFlow()
                .onStart {
                    _characterList.value = emptyList()
                }
                .collect { characters ->
                    _characterList.value = characters
                }
        }
    }
}