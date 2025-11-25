package org.sopt.korailtalk.domain.model
import org.sopt.korailtalk.R

data class DummyModel(
    val convertedData : Int
)

data class GridItemData(
    val iconId: Int,
    val title: String
)

val items = listOf(
    GridItemData(R.drawable.ic_navigation, "길안내"),
    GridItemData(R.drawable.ic_location, "열차위치"),
    GridItemData(R.drawable.ic_parking, "주차"),
    GridItemData(R.drawable.ic_bus, "공항버스"),
    GridItemData(R.drawable.ic_car, "렌터카"),
    GridItemData(R.drawable.ic_car_sharing, "카셰어링"),
    GridItemData(R.drawable.ic_carrier, "짐배송"),
    GridItemData(R.drawable.ic_bakery, "카페&빵"),
    GridItemData(R.drawable.ic_ticket, "레저이용권"),
    GridItemData(R.drawable.ic_taxi, "관광택시")
)
