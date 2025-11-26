package org.sopt.korailtalk.presentation.checkout.state

import org.sopt.korailtalk.domain.model.DomainTrainInfo


sealed interface CheckoutUiState {
    data object Init : CheckoutUiState
    data object Loading : CheckoutUiState
    data class Success(
        val domainTrainInfo: DomainTrainInfo
    ) : CheckoutUiState
    data class Failure(val message: String) : CheckoutUiState
}