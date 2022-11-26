package com.anastasiaiu.dttrealestate.model.remote

import kotlinx.serialization.*

@Serializable
class House(
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