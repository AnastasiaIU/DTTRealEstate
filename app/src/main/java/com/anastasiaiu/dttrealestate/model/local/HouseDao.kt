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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(houses: List<House>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouse(house: House)

    @Query("SELECT * FROM houses ORDER BY price")
    fun getAllHouses(): Flow<List<House>>

    @Query("SELECT * FROM houses WHERE is_bookmarked = 1 ORDER BY price")
    fun getAllBookmarkedHouses(): Flow<List<House>>

    @Query(
        "SELECT * FROM houses WHERE city LIKE '%' || :searchQuery || '%' " +
                "OR zip LIKE '%' || :searchQuery || '%' ORDER BY price"
    )
    suspend fun searchAtDatabase(searchQuery: String): List<House>
}