package com.anastasiaiu.dttrealestate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import com.anastasiaiu.dttrealestate.model.ModelConstants.BASE_URL
import com.anastasiaiu.dttrealestate.model.ModelConstants.HOUSES_TABLE_NAME

/**
 * [House] data class represents the response object and the Room entity of the database table.
 *
 * @property id the ID of the [House].
 * @property imageUrl the image URL of the [House] picture.
 * @property price the price of the [House].
 * @property bedrooms the amount of bedrooms at the [House].
 * @property bathrooms the amount of bathrooms at the [House].
 * @property size the size of the [House].
 * @property description the description of the [House].
 * @property zip the ZIP code of the [House].
 * @property city the city where is the [House] placed.
 * @property latitude the latitude of the [House].
 * @property longitude the longitude of the [House].
 * @property isBookmarked contains the state of the [House] of being bookmarked or not.
 */
@Entity(tableName = HOUSES_TABLE_NAME)
@Serializable
data class House(
    @PrimaryKey
    val id: Int,
    val image: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String = "$BASE_URL$image",
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val size: Int,
    val description: String,
    var zip: String,
    val city: String,
    val latitude: Int,
    val longitude: Int,
    val createdDate: String,
    @ColumnInfo(name = "is_bookmarked")
    var isBookmarked: Boolean = false
)