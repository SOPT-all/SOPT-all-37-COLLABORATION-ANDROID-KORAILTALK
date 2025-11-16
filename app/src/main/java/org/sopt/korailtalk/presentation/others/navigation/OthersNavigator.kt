package org.sopt.korailtalk.presentation.others.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sopt.korailtalk.core.navigation.MainTabRoute
import org.sopt.korailtalk.presentation.others.SaleRoute
import org.sopt.korailtalk.presentation.others.ProductRoute
import org.sopt.korailtalk.presentation.others.TicketRoute

fun NavController.navigateToSale(navOptions: NavOptions) {
    navigate(MainTabRoute.Sale, navOptions)
}

fun NavController.navigateToProduct(navOptions: NavOptions) {
    navigate(MainTabRoute.Product, navOptions)
}

fun NavController.navigateToTicket(navOptions: NavOptions) {
    navigate(MainTabRoute.Ticket, navOptions)
}

fun NavGraphBuilder.saleNavGraph(
    paddingValues: PaddingValues
) {
    composable<MainTabRoute.Sale> {
        SaleRoute(
            paddingValues = paddingValues
        )
    }
}

fun NavGraphBuilder.productNavGraph(
    paddingValues: PaddingValues
) {
    composable<MainTabRoute.Product> {
        ProductRoute(
            paddingValues = paddingValues
        )
    }
}

fun NavGraphBuilder.ticketNavGraph(
    paddingValues: PaddingValues
) {
    composable<MainTabRoute.Ticket> {
        TicketRoute(
            paddingValues = paddingValues
        )
    }
}
