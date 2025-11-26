package org.sopt.korailtalk.core.navigation

import kotlinx.serialization.Serializable

interface Route {
    @Serializable
    data object Checkout : Route

    @Serializable
    data class Reservation(
        val origin: String,
        val destination: String
    ) : Route
}