package com.c314.radiantprojects.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Result(
    @field:SerializedName("confidence")
    val confidence: String,
    @field:SerializedName("prediction")
    val prediction: String,
)
