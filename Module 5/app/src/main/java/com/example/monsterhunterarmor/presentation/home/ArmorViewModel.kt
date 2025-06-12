package com.example.monsterhunterarmor.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monsterhunterarmor.data.local.ArmorEntity
import com.example.monsterhunterarmor.data.remote.ApiResponse
import com.example.monsterhunterarmor.repository.ArmorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArmorViewModel(private val repository: ArmorRepository) : ViewModel() {

    private val _armorState = MutableStateFlow<ApiResponse<Flow<List<ArmorEntity>>>>(ApiResponse.Loading)
    val armorState: StateFlow<ApiResponse<Flow<List<ArmorEntity>>>> = _armorState

    private val _eventFlow = MutableSharedFlow<ViewEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchArmor()
    }

    fun fetchArmor() {
        viewModelScope.launch {
            repository.getArmorList().collectLatest {
                _armorState.value = it
            }
        }
    }

    fun onDetailButtonClicked(armor: ArmorEntity) {
        Log.d("ArmorViewModel", "Detail button clicked for: ${armor.name}")
        viewModelScope.launch {
            _eventFlow.emit(ViewEvent.NavigateToDetail(armor))
        }
    }

    fun onSearchButtonClicked(armor: ArmorEntity) {
        Log.d("ArmorViewModel", "Search button clicked for: ${armor.name}")
        viewModelScope.launch {
            _eventFlow.emit(ViewEvent.OpenBrowser(armor.name))
        }
    }

    sealed class ViewEvent {
        data class NavigateToDetail(val armor: ArmorEntity) : ViewEvent()
        data class OpenBrowser(val query: String) : ViewEvent()
    }
}