package com.anastasiaiu.dttrealestate.model.repository

import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.model.local.HouseDao
import com.anastasiaiu.dttrealestate.model.remote.RetrofitService
import kotlinx.coroutines.flow.Flow

/**
 * [HouseRepository] class provides access to the data layer of the application.
 */
class HouseRepository(private val houseDao: HouseDao) {

    suspend fun getHousesFromServer(): List<House> {
        return RetrofitService.instance.getHouses()
    }

    suspend fun addAllHousesToDatabase(houses: List<House>) {
        houseDao.insertAll(houses)
    }

    fun getAllHousesFromDatabase(): Flow<List<House>> {
        return houseDao.getAllHouses()
    }

    suspend fun addHouseToDatabase(house: House) {
        houseDao.insertHouse(house)
    }

    fun getAllBookmarkedHouses(): Flow<List<House>> {
        return houseDao.getAllBookmarkedHouses()
    }

    suspend fun searchAtDatabase(searchQuery: String): List<House> {
        return houseDao.searchAtDatabase(searchQuery)
    }
}