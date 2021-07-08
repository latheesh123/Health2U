package com.example.health2u.service

import com.example.health2u.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ):Response<NewsResponse>

}