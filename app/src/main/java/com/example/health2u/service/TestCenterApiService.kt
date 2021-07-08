package com.example.health2u.service

import com.example.health2u.model.CenterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestCenterApiService {
    @GET("discover")
    suspend fun getCenters(
        @Query("apikey") apiKey: String,
        @Query("q") q: String,
        @Query("at") latlong: String,
        @Query("limit") limit: String,
    ): Response<CenterResponse>

}