package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DailyPictureViewModel @Inject constructor(
    private val getPODUseCase: GetPODUseCase,
    private val requestPODListUseCase: RequestPODListUseCase,
    private val requestPODSingleDayUseCase: RequestPODSingleDayUseCase,
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
                        _state.update {
                            _state.value.copy(loading = false,
                                dailyPictures = items.sortedByDescending { it.date })
                        }
                    }
                }
        }
        viewModelScope.launch {
            getLastPODUpdateDateUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { info ->
                    if (info == null) {
                        launchListCompleteRequest()
                        updateLastPODUpdateUseCase(LastUpdateInfo(0, Calendar.getInstance(), false))
                    } else if (info.updateNeed) {
                        updateLastPODUpdateUseCase(info.apply {
                            updateNeed = false
                        })
                        launchDayRequest()
                    }
                }
        }
    }

    fun launchListCompleteRequest() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true, dailyPictures = emptyList()) }
            _state.value = UiState(
                loading = false,
                dailyPictures = emptyList(),
                error = requestPODListUseCase()
            )
        }
    }

    fun launchDayRequest() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            delay(3000)
            _state.update {
                _state.value.copy(
                    loading = false,
                    error = requestPODSingleDayUseCase()
                )
            }
        }
    }


    data class UiState(
        val loading: Boolean = false,
        val dailyPictures: List<PictureOfDayItem>? = null,
        val error: Error? = null
    )
}