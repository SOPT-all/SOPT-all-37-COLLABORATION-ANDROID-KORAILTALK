package org.sopt.korailtalk.presentation.reservation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sopt.korailtalk.core.navigation.Route
import org.sopt.korailtalk.presentation.reservation.screen.ReservationRoute

/**
 * Reservation 화면으로 이동
 */

fun NavController.navigateToReservation(navOptions: NavOptions? = null) {
    navigate(Route.Reservation, navOptions)
}

fun NavGraphBuilder.reservationNavGraph(
    paddingValues: PaddingValues,
    navigateToCheckout: (seatType: String, trainId: String) -> Unit,
    navigateUp: () -> Unit
) {
    composable<Route.Reservation> {
        ReservationRoute(
            paddingValues = paddingValues,
            navigateToCheckout = navigateToCheckout,
            navigateUp = navigateUp
        )
    }
}

