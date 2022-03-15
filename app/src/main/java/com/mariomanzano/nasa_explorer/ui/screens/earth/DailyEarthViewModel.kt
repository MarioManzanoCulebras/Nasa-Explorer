package com.mariomanzano.nasa_explorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.toError
import com.mariomanzano.nasa_explorer.usecases.GetEarthUseCase
import com.mariomanzano.nasa_explorer.usecases.RequestEarthListUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DailyEarthViewModel(
    getEarthUseCase: GetEarthUseCase,
    private val requestEarthListUseCase: RequestEarthListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getEarthUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { items -> _state.update { UiState(dailyPictures = items) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestEarthListUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val dailyPictures: List<EarthItem>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class DailyEarthViewModelFactory(
    private val getEarthUseCase: GetEarthUseCase,
    private val requestEarthListUseCase: RequestEarthListUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyEarthViewModel(getEarthUseCase, requestEarthListUseCase) as T
    }
}