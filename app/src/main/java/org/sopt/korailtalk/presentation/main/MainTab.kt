package org.sopt.korailtalk.presentation.main

import androidx.annotation.DrawableRes
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.navigation.MainTabRoute
import org.sopt.korailtalk.core.navigation.Route

internal enum class MainTab(
    @DrawableRes val iconResId: Int,
    val route: MainTabRoute,
    val label: String
) {
    HOME(
        iconResId = R.drawable.ic_maintab_home,
        route = MainTabRoute.Home,
        label = "홈"
    ),
    SALE(
        iconResId = R.drawable.ic_maintab_sale,
        route = MainTabRoute.Sale,
        label = "할인"
    ),
    PRODUCT(
        iconResId = R.drawable.ic_maintab_product,
        route = MainTabRoute.Product,
        label = "여행상품 패스"
    ),
    TICKET(
        iconResId = R.drawable.ic_maintab_ticket,
        route = MainTabRoute.Ticket,
        label = "상품티켓"
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): MainTab? {
            return MainTab.entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return MainTab.entries.map { it.route }.any { predicate(it) }
        }
    }
}