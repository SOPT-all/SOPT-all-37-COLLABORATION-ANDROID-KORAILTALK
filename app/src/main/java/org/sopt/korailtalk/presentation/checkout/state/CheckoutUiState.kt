package org.sopt.korailtalk.presentation.checkout.state

import org.sopt.korailtalk.domain.model.DomainNationalVerify
import org.sopt.korailtalk.domain.model.DomainTrainInfo


sealed interface CheckoutUiState {
    data object Init : CheckoutUiState
    data object Loading : CheckoutUiState
    data class Success(
        val domainTrainInfo: DomainTrainInfo,
//        val nationalVerify: DomainNationalVerify
    ) : CheckoutUiState
    data class Failure(val message: String) : CheckoutUiState
}