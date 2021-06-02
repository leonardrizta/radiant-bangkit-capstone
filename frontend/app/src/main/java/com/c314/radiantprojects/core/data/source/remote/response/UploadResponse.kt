package com.c314.radiantprojects.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @field:SerializedName("data")
    val result : Result,
    val isError: Boolean,
    val message: String,
    val statusCode: Int
)

