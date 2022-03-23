package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfter
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyPictureViewModel(
    getPODUseCase: GetPODUseCase,
    private val requestPODListUseCase: RequestPODListUseCase,
    private val resetPODListUseCase: ResetPODListUseCase,
    private val getLastPODUpdateUseCase: GetLastPODUpdateUseCase,
    private val updateLastUpdateUseCase: UpdateLastUpdateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private var requestedUpdate = false

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getPODUseCase()
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
            getLastPODUpdateUseCase()
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
                            resetPODListUseCase()
                            requestedUpdate = true
                        }
                    }
                }
        }
    }

    private fun requestUpdate(request: Boolean) {
        viewModelScope.launch {
            if (request) {
                requestPODListUseCase()
                requestedUpdate = false
            }
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
    private val requestPODListUseCase: RequestPODListUseCase,
    private val resetPODListUseCase: ResetPODListUseCase,
    private val getLastPODUpdateUseCase: GetLastPODUpdateUseCase,
    private val updateLastUpdateUseCase: UpdateLastUpdateUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyPictureViewModel(
            getPODUseCase,
            requestPODListUseCase,
            resetPODListUseCase,
            getLastPODUpdateUseCase,
            updateLastUpdateUseCase
        ) as T
    }
}