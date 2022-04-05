package com.mariomanzano.nasaexplorer.ui.screens.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetLastMarsUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.GetMarsUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestMarsListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastMarsUpdateUseCase
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
class MarsViewModel @Inject constructor(
    getMarsUseCase: GetMarsUseCase,
    private val requestMarsListUseCase: RequestMarsListUseCase,
    private val getLastMarsUpdateDateUseCase: GetLastMarsUpdateDateUseCase,
    private val updateLastMarsUpdateUseCase: UpdateLastMarsUpdateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getMarsUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { items ->
                    if (items.isNotEmpty()) {
                        _state.update {
                            _state.value.copy(loading = false,
                                marsPictures = items.sortedByDescending { it.date })
                        }
                    }
                }
        }
        viewModelScope.launch {
            getLastMarsUpdateDateUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { info ->
                    if (info == null) {
                        updateLastMarsUpdateUseCase(
                            LastUpdateInfo(
                                0,
                                Calendar.getInstance(),
                                false
                            )
                        )
                        launchUpdate()
                    } else if (info.updateNeed) {
                        updateLastMarsUpdateUseCase(info.apply { updateNeed = false })
                        launchUpdate()
                    }
                }
        }
    }

    fun launchUpdate() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            delay(3000)
            _state.update { _state.value.copy(loading = false, error = requestMarsListUseCase()) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val marsPictures: List<MarsItem>? = null,
        val error: Error? = null
    )
}