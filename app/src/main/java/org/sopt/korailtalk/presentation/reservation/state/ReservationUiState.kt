package org.sopt.korailtalk.presentation.reservation.state

import org.sopt.korailtalk.domain.model.DomainTrainItem

sealed interface ReservationUiState {
    data object Initial : ReservationUiState
    data object Loading : ReservationUiState
    data class Success(
        val trains: List<DomainTrainItem>,
        val origin: String,
        val destination: String,
        val totalTrains: Int
    ) : ReservationUiState
    data class Error(val message: String) : ReservationUiState
}
