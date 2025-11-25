package org.sopt.korailtalk.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.korailtalk.core.common.state.UiState
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.presentation.checkout.state.CheckoutUiState
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val korailTalkRepository: KorailTalkRepository
) : ViewModel() {

    private val _trainInfo = MutableStateFlow<UiState<DomainTrainInfo>>(UiState.Init)

    val checkoutUiState: StateFlow<CheckoutUiState> =
        combine(_trainInfo) { loadState ->
            CheckoutUiState(
                trainInfoLoadState = loadState[0]
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CheckoutUiState(
                trainInfoLoadState = UiState.Init
            )
        )

    fun getTrainInfo(seatType: SeatType, trainId: Long) = viewModelScope.launch {
        val result = korailTalkRepository.getTrainInfo(DomainTrainInfoRequest(seatType), trainId)

        result.fold(
            onSuccess = {
                _trainInfo.emit(UiState.Success(it))
            },
            onFailure = {

            }
        )
    }
}