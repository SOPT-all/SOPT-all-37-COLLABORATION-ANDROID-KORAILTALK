package org.sopt.korailtalk.presentation.checkout.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sopt.korailtalk.core.navigation.Route
import org.sopt.korailtalk.presentation.checkout.CheckoutRoute


fun NavController.navigateToCheckout(navOptions: NavOptions? = null) {
    navigate(Route.Checkout, navOptions)
}

fun NavGraphBuilder.checkoutNavGraph(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    composable<Route.Checkout> {
        CheckoutRoute(
            paddingValues = paddingValues,
            navigateToHome = navigateToHome,
            navigateUp = navigateUp
        )
    }
}