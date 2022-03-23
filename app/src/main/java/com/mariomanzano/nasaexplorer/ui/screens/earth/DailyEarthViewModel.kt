package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfter
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyEarthViewModel(
    getEarthUseCase: GetEarthUseCase,
    private val requestEarthListUseCase: RequestEarthListUseCase,
    private val resetEarthListUseCase: ResetEarthListUseCase,
    private val getLastEarthUpdateUseCase: GetLastEarthUpdateUseCase,
    private val updateLastUpdateUseCase: UpdateLastUpdateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private var requestedUpdate = false

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getEarthUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { items ->
                    if (requestedUpdate) {
                        _state.update {
                            _state.value.copy(
                                loading = true,
                                dailyPictures = emptyList()
                            )
                        }
                        requestUpdate(requestedUpdate)
                    } else if (items.isNotEmpty()) {
                        _state.update { _state.value.copy(loading = false, dailyPictures = items) }
                    }
                }
        }
        viewModelScope.launch {
            getLastEarthUpdateUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { date ->
                    when {
                        date == null -> {
                            updateLastUpdateUseCase()
                            requestUpdate(true)
                        }
                        date.checkIfDayAfter() -> {
                            resetEarthListUseCase()
                            requestedUpdate = true
                        }
                    }
                }
        }
    }

    private fun requestUpdate(request: Boolean) {
        viewModelScope.launch {
            if (request) {
                requestEarthListUseCase()
                requestedUpdate = false
            }
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
    private val requestEarthListUseCase: RequestEarthListUseCase,
    private val resetEarthListUseCase: ResetEarthListUseCase,
    private val getLastEarthUpdateUseCase: GetLastEarthUpdateUseCase,
    private val updateLastUpdateUseCase: UpdateLastUpdateUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyEarthViewModel(
            getEarthUseCase,
            requestEarthListUseCase,
            resetEarthListUseCase,
            getLastEarthUpdateUseCase,
            updateLastUpdateUseCase
        ) as T
    }
}