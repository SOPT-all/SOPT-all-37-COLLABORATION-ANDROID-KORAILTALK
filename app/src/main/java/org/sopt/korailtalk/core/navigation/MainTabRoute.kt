package org.sopt.korailtalk.core.navigation

import kotlinx.serialization.Serializable

interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Sale : MainTabRoute

    @Serializable
    data object Product : MainTabRoute

    @Serializable
    data object Ticket : MainTabRoute
}
