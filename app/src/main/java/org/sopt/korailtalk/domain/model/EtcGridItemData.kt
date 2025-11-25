package org.sopt.korailtalk.domain.model
import kotlinx.collections.immutable.persistentListOf
import org.sopt.korailtalk.R

data class EtcGridItemData(
    val iconId: Int,
    val title: String
)

val items = persistentListOf(
    EtcGridItemData(R.drawable.ic_home_navigation, "길안내"),
    EtcGridItemData(R.drawable.ic_home_location, "열차위치"),
    EtcGridItemData(R.drawable.ic_home_parking, "주차"),
    EtcGridItemData(R.drawable.ic_home_bus, "공항버스"),
    EtcGridItemData(R.drawable.ic_home_car, "렌터카"),
    EtcGridItemData(R.drawable.ic_home_car_sharing, "카셰어링"),
    EtcGridItemData(R.drawable.ic_home_carrier, "짐배송"),
    EtcGridItemData(R.drawable.ic_home_bakery, "카페&빵"),
    EtcGridItemData(R.drawable.ic_home_ticket, "레저이용권"),
    EtcGridItemData(R.drawable.ic_home_taxi, "관광택시")
)
