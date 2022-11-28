package com.anastasiaiu.dttrealestate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import com.anastasiaiu.dttrealestate.model.Constants.BASE_URL
import com.anastasiaiu.dttrealestate.model.Constants.HOUSES_TABLE_NAME

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
    val zip: String,
    val city: String,
    val latitude: Int,
    val longitude: Int,
    val createdDate: String,
    @ColumnInfo(name = "is_bookmarked")
    var isBookMarked: Boolean = false
)