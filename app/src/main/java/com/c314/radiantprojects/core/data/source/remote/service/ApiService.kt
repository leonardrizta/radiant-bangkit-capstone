package com.c314.radiantprojects.core.data.source.remote.service

import com.c314.radiantprojects.core.data.source.remote.response.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=us&category=health")
    suspend fun getArticles(@Query("apiKey") apiKey : String) : ArticlesResponse
}