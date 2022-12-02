package com.anastasiaiu.dttrealestate

import android.app.Application
import com.anastasiaiu.dttrealestate.model.local.RealEstateDatabase
import com.anastasiaiu.dttrealestate.model.repository.HouseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * [DttRealEstateApplication] provides single instances of
 * the database and of the repository for the application.
 */
class DttRealEstateApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { RealEstateDatabase.getInstance(this, applicationScope) }

    val repository by lazy { HouseRepository(database.houseDao()) }
}