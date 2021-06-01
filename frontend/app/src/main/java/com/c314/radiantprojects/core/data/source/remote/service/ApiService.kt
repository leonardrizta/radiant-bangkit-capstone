package com.c314.radiantprojects.core.data.source.remote.service

import com.c314.radiantprojects.core.data.source.remote.response.LatestInfoResponse
import com.c314.radiantprojects.core.data.source.remote.response.ResultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("top-headlines?country=us&category=health")
    suspend fun getLatestInfo(@Query("apiKey") apiKey : String) : LatestInfoResponse

    @Multipart
    @POST("api/predict")
    fun uploadImage(
        @Part("key") key: RequestBody?,
        @Part("type") type: RequestBody?,
        @Part src : MultipartBody.Part
    ): Call<ResultResponse>
}