package org.sopt.korailtalk.core.navigation

import kotlinx.serialization.Serializable

interface Route {
    @Serializable
    data class Checkout(
        val seatType: String,
        val trainId: String,
        val normalSeatPrice: Int,
        val premiumSeatPrice: Int?
    ) : Route

    @Serializable
    data class Reservation(
        val origin: String,
        val destination: String
    ) : Route
}