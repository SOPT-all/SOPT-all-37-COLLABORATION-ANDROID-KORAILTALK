package org.sopt.korailtalk.presentation.reservation.state

import kotlinx.collections.immutable.ImmutableList
import org.sopt.korailtalk.core.common.state.UiState
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.type.SeatType

data class ReservationUiState(
    val origin: String,
    val destination: String,
    val trains: UiState<ImmutableList<DomainTrainItem>> = UiState.Init,
    val totalTrains: Int = 0,
    val date: String = "12월 1일",
    val dayOfWeek: String = "금",
    val passengerCount: Int = 1,
    val nextCursor: String? = null
)

/**
 * 바텀시트 상태
 */
data class BottomSheetState(
    val isVisible: Boolean = false,
    val selectedTrain: DomainTrainItem? = null,
    val selectedSeatType: SeatType? = null
)
