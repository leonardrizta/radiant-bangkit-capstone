package com.c314.radiantprojects.core.data.source.remote.service

import com.c314.radiantprojects.core.data.source.remote.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostService {

    @Multipart
    @POST("api/predict")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody,
        @Part("isError") isError: RequestBody,
        @Part("message") message: RequestBody,
        @Part("statusCode") statusCode: RequestBody
    ): Call<UploadResponse>
}
