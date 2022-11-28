package com.anastasiaiu.dttrealestate.model.repository

import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.model.remote.RetrofitService

class HouseRepository() {

    suspend fun getHouses(): List<House> {
        return RetrofitService.instance.getHouses()
    }
}