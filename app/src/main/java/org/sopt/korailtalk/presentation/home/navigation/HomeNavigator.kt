package org.sopt.korailtalk.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sopt.korailtalk.core.navigation.MainTabRoute
import org.sopt.korailtalk.presentation.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    paddingValues: PaddingValues,
    navigateToReservation: () -> Unit // TODO: 에약 정보 전달
) {
    composable<MainTabRoute.Home> {
        HomeRoute(
            paddingValues = paddingValues,
            navigateToReservation = navigateToReservation
        )
    }
}