package org.sopt.korailtalk.presentation.checkout.state

import org.sopt.korailtalk.core.common.state.UiState
import org.sopt.korailtalk.domain.model.DomainTrainInfo

data class CheckoutUiState(
    val trainInfoLoadState: UiState<DomainTrainInfo>
) {
    val loadState: UiState<Unit>
        get() = when (trainInfoLoadState) {
            is UiState.Success -> UiState.Success(Unit)
            is UiState.Init -> UiState.Init
            is UiState.Loading -> UiState.Loading
            else -> UiState.Failure("")
        }
}
