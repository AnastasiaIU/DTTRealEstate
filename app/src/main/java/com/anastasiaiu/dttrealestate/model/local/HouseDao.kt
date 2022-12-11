package com.anastasiaiu.dttrealestate.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anastasiaiu.dttrealestate.model.House
import kotlinx.coroutines.flow.Flow

/**
 * Data access object to interact with the database.
 */
@Dao
interface HouseDao {

    /**
     * Inserts all [houses] in the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListOfHouses(houses: List<House>)

    /**
     * Inserts the [house] in the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouse(house: House)

    /**
     * Returns all houses from the database ordered by price in ascending order.
     * @return the list of [House] as flow data stream.
     */
    @Query("SELECT * FROM houses ORDER BY price")
    fun getAllHousesAsFlow(): Flow<List<House>>

    /**
     * Returns all houses from the database ordered by price in ascending order.
     * @return the list of [House].
     */
    @Query("SELECT * FROM houses ORDER BY price")
    suspend fun getAllHousesList(): List<House>

    /**
     * Returns all bookmarked houses from the database ordered by price in ascending order.
     * @return the list of [House] as flow data stream.
     */
    @Query("SELECT * FROM houses WHERE is_bookmarked = 1 ORDER BY price")
    fun getAllBookmarkedHouses(): Flow<List<House>>

    /**
     * Performs a search at the database by [searchQuery] at zip and city columns.
     * @return result of search as a list of [House] ordered by price in ascending order.
     */
    @Query(
        "SELECT * FROM houses WHERE city LIKE '%' || :searchQuery || '%' " +
                "OR zip LIKE '%' || :searchQuery || '%' ORDER BY price"
    )
    suspend fun searchAtDatabase(searchQuery: String): List<House>
}