package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.GetLastEarthUpdateDateNeedUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestEarthListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastEarthUpdateUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class DailyEarthViewModel(
    getEarthUseCase: GetEarthUseCase,
    private val requestEarthListUseCase: RequestEarthListUseCase,
    private val getLastEarthUpdateDateNeedUseCase: GetLastEarthUpdateDateNeedUseCase,
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
            getLastEarthUpdateDateNeedUseCase()
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
                    } else if (info.updateNeed) {
                        updateLastEarthUpdateUseCase(info.apply { updateNeed = false })
                    }
                    launchUpdate()
                }
        }
    }

    fun launchUpdate() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            requestEarthListUseCase()
            delay(3000)
            _state.update { _state.value.copy(loading = false) }
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
    private val getLastEarthUpdateDateNeedUseCase: GetLastEarthUpdateDateNeedUseCase,
    private val updateLastEarthUpdateUseCase: UpdateLastEarthUpdateUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyEarthViewModel(
            getEarthUseCase,
            requestEarthListUseCase,
            getLastEarthUpdateDateNeedUseCase,
            updateLastEarthUpdateUseCase
        ) as T
    }
}