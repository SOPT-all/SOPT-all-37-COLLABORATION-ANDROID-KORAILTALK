package org.sopt.korailtalk.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
):ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // 역 위치를 서로 바꾸는 로직
    fun swapStations() {
        _uiState.update { currentState ->
            currentState.copy(
                startStation = currentState.endStation,
                endStation = currentState.startStation
            )
        }
    }
}
