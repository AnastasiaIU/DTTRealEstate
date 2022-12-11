package com.anastasiaiu.dttrealestate

import android.app.Application
import com.anastasiaiu.dttrealestate.model.local.RealEstateDatabase
import com.anastasiaiu.dttrealestate.model.repository.HouseRepository

/**
 * [DttRealEstateApplication] provides single instances of
 * the database and of the repository for the application.
 */
class DttRealEstateApplication : Application() {

    private val database by lazy { RealEstateDatabase.getInstance(this) }

    /**
     * An instance of [HouseRepository] class which
     * provides access to the data layer of the application.
     */
    val repository by lazy { HouseRepository(database.houseDao()) }
}