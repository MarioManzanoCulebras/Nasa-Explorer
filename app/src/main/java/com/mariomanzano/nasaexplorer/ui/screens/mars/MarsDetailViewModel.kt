package com.mariomanzano.nasaexplorer.ui.screens.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.FindMarsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
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