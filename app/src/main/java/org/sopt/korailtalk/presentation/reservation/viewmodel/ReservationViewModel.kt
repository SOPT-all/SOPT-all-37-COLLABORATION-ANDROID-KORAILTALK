package org.sopt.korailtalk.presentation.reservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import org.sopt.korailtalk.presentation.reservation.state.ReservationUiState
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val repository: KorailTalkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReservationUiState>(ReservationUiState.Initial)
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    fun searchTrains(
        origin: String,
        destination: String,
        trainType: String? = null,
        seatType: String? = null,
        isBookAvailable: Boolean? = null
    ) {
        viewModelScope.launch {
            _uiState.value = ReservationUiState.Loading

            repository.getTrainList(
                origin = origin,
                destination = destination,
                trainType = trainType,
                seatType = seatType,
                isBookAvailable = isBookAvailable
            ).onSuccess { result ->
                _uiState.value = ReservationUiState.Success(
                    trains = result.trains,
                    origin = result.origin,
                    destination = result.destination,
                    totalTrains = result.totalTrains
                )
            }.onFailure { error ->
                _uiState.value = ReservationUiState.Error(
                    message = error.message ?: "알 수 없는 오류가 발생했습니다"
                )
            }
        }
    }
}
