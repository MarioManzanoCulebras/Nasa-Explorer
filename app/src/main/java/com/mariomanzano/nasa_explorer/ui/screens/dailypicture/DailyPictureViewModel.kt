package com.mariomanzano.nasa_explorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.toError
import com.mariomanzano.nasa_explorer.usecases.GetPODUseCase
import com.mariomanzano.nasa_explorer.usecases.RequestPODListUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DailyPictureViewModel(
    getPODUseCase: GetPODUseCase,
    private val requestPODListUseCase: RequestPODListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPODUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { items -> _state.update { UiState(dailyPictures = items) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPODListUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val dailyPictures: List<PictureOfDayItem>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class DailyPictureViewModelFactory(
    private val getPODUseCase: GetPODUseCase,
    private val requestPODListUseCase: RequestPODListUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyPictureViewModel(getPODUseCase, requestPODListUseCase) as T
    }
}