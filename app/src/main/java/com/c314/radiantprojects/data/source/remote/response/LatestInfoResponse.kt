package com.c314.radiantprojects.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LatestInfoResponse(
    @SerializedName("articles")
    val articles: List<LatestInfo>
)
