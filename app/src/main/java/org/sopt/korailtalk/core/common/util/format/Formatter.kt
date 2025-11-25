package org.sopt.korailtalk.core.common.util.format

fun Int.priceFormat() : String {
    val formatter = java.text.DecimalFormat("#,###")
    return formatter.format(this)
}