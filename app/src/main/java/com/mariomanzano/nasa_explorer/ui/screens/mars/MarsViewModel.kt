package com.mariomanzano.nasa_explorer.ui.screens.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.entities.toError
import com.mariomanzano.nasa_explorer.usecases.GetMarsUseCase
import com.mariomanzano.nasa_explorer.usecases.RequestMarsListUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MarsViewModel(
    getMarsUseCase: GetMarsUseCase,
    private val requestMarsListUseCase: RequestMarsListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getMarsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { items -> _state.update { UiState(marsPictures = items) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestMarsListUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val marsPictures: List<MarsItem>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MarsViewModelFactory(
    private val getMarsUseCase: GetMarsUseCase,
    private val requestMarsListUseCase: RequestMarsListUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarsViewModel(getMarsUseCase, requestMarsListUseCase) as T
    }
}