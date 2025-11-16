package org.sopt.korailtalk.core.navigation

import kotlinx.serialization.Serializable

interface Route {
    @Serializable
    data object Checkout : Route

    @Serializable
    data object Reservation : Route
}