package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfter
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestEarthListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
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
            val error = requestEarthListUseCase(dayChanged)
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