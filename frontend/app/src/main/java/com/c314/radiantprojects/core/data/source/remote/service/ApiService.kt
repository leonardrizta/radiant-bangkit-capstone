package com.c314.radiantprojects.core.data.source.remote.service

import com.c314.radiantprojects.core.data.source.remote.response.LatestInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=us&category=health")
    suspend fun getLatestInfo(@Query("apiKey") apiKey : String) : LatestInfoResponse
}