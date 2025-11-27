package org.sopt.korailtalk.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.korailtalk.domain.model.DomainNationalVerify
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.presentation.checkout.state.CheckoutUiState
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val korailTalkRepository: KorailTalkRepository
) : ViewModel() {

    private val _checkoutUiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Init)
    val checkoutUiState = _checkoutUiState.asStateFlow()

    private val _checkoutSideEffect = MutableStateFlow<CheckoutSideEffect?>(null)
    val checkoutSideEffect = _checkoutSideEffect.asStateFlow()

    fun getTrainInfo(seatType: SeatType, trainId: Long) = viewModelScope.launch {
        val result = korailTalkRepository.getTrainInfo(DomainTrainInfoRequest(seatType), trainId)

        result.fold(
            onSuccess = {
                _checkoutUiState.emit(CheckoutUiState.Success(it))
            },
            onFailure = {

            }
        )
    }

    fun postVerifyNation(domainNationalVerify: DomainNationalVerify) {
        viewModelScope.launch {
            korailTalkRepository.postVerifyNational(domainNationalVerify)
                .fold(
                    onSuccess = { isSuccess ->
                        _checkoutSideEffect.emit(
                            CheckoutSideEffect.ShowDialog(
                                if (isSuccess) "인증되었습니다." else "해당 보훈번호가 인증되지 않았습니다."
                            )
                        )
                    },
                    onFailure = {
                        // TODO: 에러 처리
                    }
                )
        }
    }

    fun deleteReservation(reservationId: Long) {
        //TODO: API 호출 성공 확인
        viewModelScope.launch {
            _checkoutSideEffect.emit(CheckoutSideEffect.NavigateToHome)
        }
    }
}

sealed interface CheckoutSideEffect {
    data object NavigateToHome : CheckoutSideEffect
    data class ShowDialog(val message: String) : CheckoutSideEffect
}
