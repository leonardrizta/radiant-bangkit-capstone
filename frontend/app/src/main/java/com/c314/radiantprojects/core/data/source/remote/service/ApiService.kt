package com.c314.radiantprojects.core.data.source.remote.service

import com.c314.radiantprojects.core.data.source.remote.response.LatestInfoResponse

import retrofit2.http.*


interface ApiService {

    @GET("top-headlines?country=us&category=health")
    suspend fun getLatestInfo(@Query("apiKey") apiKey : String) : LatestInfoResponse


}