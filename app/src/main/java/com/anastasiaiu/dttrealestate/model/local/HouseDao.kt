package com.anastasiaiu.dttrealestate.model.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface HouseDao {

    @Query("SELECT * FROM houses")
    fun getAllHouses(): List<HouseEntity>
}