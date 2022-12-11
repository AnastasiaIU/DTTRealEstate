package com.anastasiaiu.dttrealestate.model.remote

import com.anastasiaiu.dttrealestate.BuildConfig.DTT_ACCESS_KEY
import com.anastasiaiu.dttrealestate.model.House
import retrofit2.http.GET
import retrofit2.http.Headers

private const val HEADER_ACCESS_KEY = "access-key: $DTT_ACCESS_KEY"

/**
 * Retrofit interface.
 */
interface HouseService {

    /**
     * Returns a list of [House] obtained from the server.
     */
    @Headers(HEADER_ACCESS_KEY)
    @GET("api/house/")
    suspend fun getHouses(): List<House>
}