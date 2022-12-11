package com.anastasiaiu.dttrealestate.model.repository

import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.model.local.HouseDao
import com.anastasiaiu.dttrealestate.model.remote.RetrofitService
import kotlinx.coroutines.flow.Flow

/**
 * [HouseRepository] class provides access to the data layer of the application.
 */
class HouseRepository(private val houseDao: HouseDao) {

    /**
     * Returns a list of [House] obtained from the server.
     */
    suspend fun getHousesFromServer(): List<House> {
        return RetrofitService.instance.getHouses()
    }

    /**
     * Inserts all [houses] in the database.
     */
    suspend fun addAllHousesToDatabase(houses: List<House>) {
        houseDao.insertListOfHouses(houses)
    }

    /**
     * Returns all houses from the database ordered by price in ascending order.
     * @return the list of [House] as flow data stream.
     */
    fun getAllHousesFromDatabase(): Flow<List<House>> {
        return houseDao.getAllHousesAsFlow()
    }

    /**
     * Returns all houses from the database ordered by price in ascending order.
     * @return the list of [House].
     */
    suspend fun getAllHousesFromDatabaseList(): List<House> {
        return houseDao.getAllHousesList()
    }

    /**
     * Inserts the [house] in the database.
     */
    suspend fun addHouseToDatabase(house: House) {
        houseDao.insertHouse(house)
    }

    /**
     * Returns all bookmarked houses from the database ordered by price in ascending order.
     * @return the list of [House] as flow data stream.
     */
    fun getAllBookmarkedHouses(): Flow<List<House>> {
        return houseDao.getAllBookmarkedHouses()
    }

    /**
     * Performs a search at the database by [searchQuery] at zip and city columns.
     * @return result of search as a list of [House] ordered by price in ascending order.
     */
    suspend fun searchAtDatabase(searchQuery: String): List<House> {
        return houseDao.searchAtDatabase(searchQuery)
    }
}