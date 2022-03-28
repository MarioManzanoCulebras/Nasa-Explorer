package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetLastPODUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.GetPODUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestPODListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastPODUpdateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyPictureViewModel(
    getPODUseCase: GetPODUseCase,
    private val requestPODListUseCase: RequestPODListUseCase,
    private val getLastPODUpdateDateUseCase: GetLastPODUpdateDateUseCase,
    private val updateLastPODUpdateUseCase: UpdateLastPODUpdateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getPODUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { items ->
                    if (items.isNotEmpty()) {
                        _state.update { _state.value.copy(loading = false, dailyPictures = items) }
                    }
                }
        }
        viewModelScope.launch {
            getLastPODUpdateDateUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { info ->
                    if (info?.updateNeed == true) {
                        updateLastPODUpdateUseCase(info.apply {
                            updateNeed = false
                        })
                        launchUpdate()
                    }
                }
        }
    }

    private fun launchUpdate() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true, dailyPictures = emptyList()) }
            requestPODListUseCase()
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
    private val getLastPODUpdateDateUseCase: GetLastPODUpdateDateUseCase,
    private val updateLastPODUpdateUseCase: UpdateLastPODUpdateUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyPictureViewModel(
            getPODUseCase,
            requestPODListUseCase,
            getLastPODUpdateDateUseCase,
            updateLastPODUpdateUseCase
        ) as T
    }
}