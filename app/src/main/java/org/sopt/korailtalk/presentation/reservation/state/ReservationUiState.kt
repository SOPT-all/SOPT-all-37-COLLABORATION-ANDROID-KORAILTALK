package org.sopt.korailtalk.presentation.reservation.state

import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.type.SeatType

sealed interface ReservationUiState {
    data object Initial : ReservationUiState
    data object Loading : ReservationUiState
    data class Success(
        val trains: List<DomainTrainItem>,
        val origin: String,
        val destination: String,
        val totalTrains: Int,
        val filteredTrains: List<DomainTrainItem> = trains,
        val date: String = "11월 10일",
        val dayOfWeek: String = "화",
        val passengerCount: Int = 1,
        val nextCursor: String? = null
    ) : ReservationUiState
    data class Error(val message: String) : ReservationUiState
}

/**
 * 바텀시트 상태
 */
data class BottomSheetState(
    val isVisible: Boolean = false,
    val selectedTrain: DomainTrainItem? = null,
    val selectedSeatType: SeatType? = null
)
