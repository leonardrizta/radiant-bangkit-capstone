package com.c314.radiantprojects.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LatestInfoResponse(
    @field:SerializedName("articles")
    var latestInfo: List<LatestInfo>
)