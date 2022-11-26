package com.anastasiaiu.dttrealestate.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anastasiaiu.dttrealestate.model.Constants.HOUSES_TABLE_NAME

@Entity(tableName = HOUSES_TABLE_NAME)
data class HouseEntity(
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val size: Int,
    val description: String,
    val zip: String,
    val city: String,
    val latitude: Int,
    val longitude: Int,
    val createdDate: String
)