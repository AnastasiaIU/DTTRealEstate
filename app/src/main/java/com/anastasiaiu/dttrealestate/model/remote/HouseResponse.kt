package com.anastasiaiu.dttrealestate.model.remote

import kotlinx.serialization.*

@Serializable
class HouseResponse(val houses: List<House>)