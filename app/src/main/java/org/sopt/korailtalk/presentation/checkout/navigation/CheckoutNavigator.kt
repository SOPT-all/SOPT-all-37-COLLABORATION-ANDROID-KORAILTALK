package org.sopt.korailtalk.presentation.checkout.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.sopt.korailtalk.core.navigation.Route
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.presentation.checkout.CheckoutRoute


// 1) navigateToCheckout 확장 함수
fun NavController.navigateToCheckout(
    seatType: String,
    trainId: String,
    normalSeatPrice: Int,
    premiumSeatPrice: Int?,
    navOptions: NavOptions? = null
) {
    navigate(Route.Checkout(seatType, trainId, normalSeatPrice, premiumSeatPrice), navOptions)
}

// 2) checkoutNavGraph 함수
fun NavGraphBuilder.checkoutNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    composable<Route.Checkout> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.Checkout>()

        CheckoutRoute(
            paddingValues = paddingValues,
            navigateToHome = navigateToHome,
            navigateUp = navigateUp,
            seatType = SeatType.valueOf(route.seatType),
            trainId = route.trainId.toLong(),
            normalSeatPrice = route.normalSeatPrice,
            premiumSeatPrice = route.premiumSeatPrice
        )
    }
}