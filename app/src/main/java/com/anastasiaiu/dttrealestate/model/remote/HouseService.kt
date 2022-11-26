package com.anastasiaiu.dttrealestate.model.remote

import retrofit2.http.GET
import retrofit2.http.Headers

private const val HEADER_ACCESS_KEY = "access-key: @{BuildConfig.DTT_ACCESS_KEY}"

interface HouseService {

    @Headers(HEADER_ACCESS_KEY)
    @GET
    suspend fun getHouses(): HouseResponse
}