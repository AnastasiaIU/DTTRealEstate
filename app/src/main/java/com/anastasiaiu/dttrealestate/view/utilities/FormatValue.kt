package com.anastasiaiu.dttrealestate.view.utilities

import com.anastasiaiu.dttrealestate.model.House
import java.util.*

/**
 * Utility object for formatting values.
 */
object FormatValue {

    /**
     * Returns formatted [price] in the format $45,000.
     */
    fun formatPrice(price: Int): String {
        return Currency.getInstance(Locale.getDefault()).symbol + "%,d".format(price)
    }

    /**
     * Returns formatted address for the provided [house] in the format 1034HB Amsterdam.
     */
    fun formatAddress(house: House): String {
        return house.zip + " " + house.city
    }

    /**
     * Returns formatted [kilometers] in the format 45.7 km.
     */
    fun formatDistance(kilometers: Double): String = "%.1f".format(kilometers) + " km"
}