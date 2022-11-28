package com.anastasiaiu.dttrealestate.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.anastasiaiu.dttrealestate.model.House

@Dao
interface HouseDao {

    @Query("SELECT * FROM houses")
    fun getAllHouses(): LiveData<List<House>>
}