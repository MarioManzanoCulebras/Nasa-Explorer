package com.mariomanzano.nasa_explorer.ui.screens.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.entities.toError
import com.mariomanzano.nasa_explorer.usecases.FindMarsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MarsDetailViewModel(
    itemId: Int,
    findMarsUseCase: FindMarsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMarsUseCase(itemId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { marsItem -> _state.update { UiState(marsItem = marsItem) } }
        }
    }

    data class UiState(
        val marsItem: MarsItem? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MareDetailViewModelFactory(
    private val itemId: Int,
    private val findMarsUseCase: FindMarsUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarsDetailViewModel(itemId, findMarsUseCase) as T
    }
}