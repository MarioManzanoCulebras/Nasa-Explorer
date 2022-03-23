package com.mariomanzano.nasaexplorer.ui.screens.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfter
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarsViewModel(
    getMarsUseCase: GetMarsUseCase,
    private val requestMarsListUseCase: RequestMarsListUseCase,
    private val resetMarsListUseCase: ResetMarsListUseCase,
    private val getLastMarsUpdateUseCase: GetLastMarsUpdateUseCase,
    private val updateLastUpdateUseCase: UpdateLastUpdateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private var requestedUpdate = false

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getMarsUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { items ->
                    if (requestedUpdate) {
                        _state.update {
                            _state.value.copy(
                                loading = true,
                                marsPictures = emptyList()
                            )
                        }
                        requestUpdate(requestedUpdate)
                    } else if (items.isNotEmpty()) {
                        _state.update { _state.value.copy(loading = false, marsPictures = items) }
                    }
                }
        }
        viewModelScope.launch {
            getLastMarsUpdateUseCase()
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
                            resetMarsListUseCase()
                            requestedUpdate = true
                        }
                    }
                }
        }
    }

    private fun requestUpdate(request: Boolean) {
        viewModelScope.launch {
            if (request) {
                requestMarsListUseCase()
                requestedUpdate = false
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val marsPictures: List<MarsItem>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MarsViewModelFactory(
    private val getMarsUseCase: GetMarsUseCase,
    private val requestMarsListUseCase: RequestMarsListUseCase,
    private val resetMarsListUseCase: ResetMarsListUseCase,
    private val getLastMarsUpdateUseCase: GetLastMarsUpdateUseCase,
    private val updateLastUpdateUseCase: UpdateLastUpdateUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarsViewModel(
            getMarsUseCase,
            requestMarsListUseCase,
            resetMarsListUseCase,
            getLastMarsUpdateUseCase,
            updateLastUpdateUseCase
        ) as T
    }
}