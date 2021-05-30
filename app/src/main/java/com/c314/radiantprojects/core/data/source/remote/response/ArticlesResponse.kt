package com.c314.radiantprojects.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("articles")
    val articles: List<Articles>
)
