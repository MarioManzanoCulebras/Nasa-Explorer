package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfter
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetPODUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestPODListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
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
                .collect { items ->
                    _state.update { UiState(dailyPictures = items) }
                    if (items.isEmpty()) {
                        onUiReady()
                    } else {
                        onUiReady(items.first().lastRequest.checkIfDayAfter())
                    }
                }
        }
    }

    private fun onUiReady(dayChanged: Boolean = false) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPODListUseCase(dayChanged)
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