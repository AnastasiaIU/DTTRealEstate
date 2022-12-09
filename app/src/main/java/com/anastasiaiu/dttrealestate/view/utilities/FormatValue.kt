package com.anastasiaiu.dttrealestate.view.utilities

import com.anastasiaiu.dttrealestate.model.House
import java.util.*

/**
 * Utility object for formatting values.
 */
object FormatValue {

    fun formatPrice(price: Int): String {
        return Currency.getInstance(Locale.getDefault()).symbol + "%,d".format(price)
    }

    fun formatAddress(house: House): String {
        return house.zip.replace(" ", "") + " " + house.city
    }

    fun formatDistance(kilometers: Double): String = "%.1f".format(kilometers) + " km"
}