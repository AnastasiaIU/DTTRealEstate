package com.anastasiaiu.dttrealestate.model.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitService {

    private val contentType = "application/json".toMediaType()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://intern.development.d-tt.dev/api/house")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
}