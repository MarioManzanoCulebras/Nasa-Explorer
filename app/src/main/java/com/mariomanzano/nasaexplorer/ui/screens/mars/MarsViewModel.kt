package com.mariomanzano.nasaexplorer.ui.screens.mars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetLastMarsUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.GetMarsUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestMarsListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastMarsUpdateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarsViewModel(
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
                        _state.update { _state.value.copy(loading = false, marsPictures = items) }
                    }
                }
        }
        viewModelScope.launch {
            getLastMarsUpdateDateUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { info ->
                    if (info?.updateNeed == true) {
                        updateLastMarsUpdateUseCase(info.apply { updateNeed = false })
                        launchUpdate()
                    }
                }
        }
    }

    private fun launchUpdate() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true, marsPictures = emptyList()) }
            requestMarsListUseCase()
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
    private val getLastMarsUpdateDateUseCase: GetLastMarsUpdateDateUseCase,
    private val updateLastMarsUpdateUseCase: UpdateLastMarsUpdateUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarsViewModel(
            getMarsUseCase,
            requestMarsListUseCase,
            getLastMarsUpdateDateUseCase,
            updateLastMarsUpdateUseCase
        ) as T
    }
}