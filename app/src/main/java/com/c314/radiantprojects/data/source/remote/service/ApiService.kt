package com.c314.radiantprojects.data.source.remote.service

import com.c314.radiantprojects.data.source.remote.response.LatestInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("country=id&category=health")
    fun getLatestInfo(@Query("api_key") apiKey : String) : Call<LatestInfoResponse>
}