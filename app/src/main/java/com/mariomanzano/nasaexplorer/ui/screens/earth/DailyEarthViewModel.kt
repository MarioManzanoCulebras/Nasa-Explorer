package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.GetLastEarthUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestEarthListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastEarthUpdateUseCase
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
class DailyEarthViewModel @Inject constructor(
    private val getEarthUseCase: GetEarthUseCase,
    private val requestEarthListUseCase: RequestEarthListUseCase,
    private val getLastEarthUpdateDateUseCase: GetLastEarthUpdateDateUseCase,
    private val updateLastEarthUpdateUseCase: UpdateLastEarthUpdateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getEarthUseCase()
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
            getLastEarthUpdateDateUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { info ->
                    if (info == null) {
                        updateLastEarthUpdateUseCase(
                            LastUpdateInfo(
                                0,
                                Calendar.getInstance(),
                                false
                            )
                        )
                        launchUpdate()
                    } else if (info.updateNeed) {
                        updateLastEarthUpdateUseCase(info.apply { updateNeed = false })
                        launchUpdate()
                    }
                }
        }
    }

    fun launchUpdate() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            delay(3000)
            _state.update { _state.value.copy(loading = false, error = requestEarthListUseCase()) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val dailyPictures: List<EarthItem>? = null,
        val error: Error? = null
    )
}