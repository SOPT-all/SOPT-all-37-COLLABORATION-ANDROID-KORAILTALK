package org.sopt.korailtalk.presentation.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import kotlinx.collections.immutable.toPersistentList
import org.sopt.korailtalk.presentation.home.navigation.homeNavGraph
import org.sopt.korailtalk.presentation.main.component.MainBottomBar
import org.sopt.korailtalk.presentation.others.navigation.productNavGraph
import org.sopt.korailtalk.presentation.others.navigation.saleNavGraph
import org.sopt.korailtalk.presentation.others.navigation.ticketNavGraph

@Composable
fun MainScreen(
    navigator: MainNavigator
) {
    val isBottomBarVisible by navigator.isBottomBarVisible.collectAsStateWithLifecycle()
    val currentTab by navigator.currentTab.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            MainBottomBar(
                visible = isBottomBarVisible,
                tabs = MainTab.entries.toPersistentList(),
                currentTab = currentTab,
                onTabSelected = navigator::navigate
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(
                paddingValues = paddingValues
            )

            // dummy
            saleNavGraph(paddingValues = paddingValues)
            productNavGraph(paddingValues = paddingValues)
            ticketNavGraph(paddingValues = paddingValues)
        }
    }
}