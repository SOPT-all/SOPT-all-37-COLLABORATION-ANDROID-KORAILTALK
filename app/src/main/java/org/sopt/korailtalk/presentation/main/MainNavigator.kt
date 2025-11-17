package org.sopt.korailtalk.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.sopt.korailtalk.core.navigation.MainTabRoute.Home
import org.sopt.korailtalk.presentation.checkout.navigation.navigateToCheckout
import org.sopt.korailtalk.presentation.home.navigation.navigateToHome
import org.sopt.korailtalk.presentation.others.navigation.navigateToProduct
import org.sopt.korailtalk.presentation.others.navigation.navigateToSale
import org.sopt.korailtalk.presentation.others.navigation.navigateToTicket
import org.sopt.korailtalk.presentation.reservation.navigation.navigateToReservation

@Stable
class MainNavigator(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val startDestination = Home

    // NavController의 Flow를 관찰하여 현재 Destination을 StateFlow로 변환
    private val currentDestination = navController.currentBackStackEntryFlow
        .map { it.destination }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    // derived state를 Composable 종속성 없이 StateFlow로 생성
    val currentTab: StateFlow<MainTab?> = currentDestination
        .map { destination ->
            MainTab.find { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    // MainTab 소속인지를 확인하며 바텀바 노출 여부 체크
    val isBottomBarVisible: StateFlow<Boolean> = currentDestination
        .map { destination ->
            MainTab.contains { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.SALE -> navController.navigateToSale(navOptions)
            MainTab.PRODUCT -> navController.navigateToProduct(navOptions)
            MainTab.TICKET -> navController.navigateToTicket(navOptions)
        }
    }

    fun navigateToHome() {
        navController.navigateToHome(
            navOptions = navOptions {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        )
    }

    fun navigateToReservation() {
        navController.navigateToReservation()
    }

    fun navigateToCheckout() {
        navController.navigateToCheckout()
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MainNavigator = remember(navController) {
    MainNavigator(navController, coroutineScope)
}