package com.anastasiaiu.dttrealestate.model.remote

import com.anastasiaiu.dttrealestate.model.ModelConstants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * The RetrofitService class generates an implementation of the HouseService interface.
 */
object RetrofitService {

    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    /**
     * The instance of Retrofit interface.
     */
    val instance: HouseService by lazy { retrofit.create(HouseService::class.java) }
}