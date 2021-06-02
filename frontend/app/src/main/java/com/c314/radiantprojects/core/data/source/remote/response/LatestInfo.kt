package com.c314.radiantprojects.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class LatestInfo(
    @field:SerializedName("author")
    var author: String,

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("description")
    var description : String,

    @field:SerializedName("urlToImage")
    var urlImage : String,

    @field:SerializedName("publishedAt")
    var publishedAt : String,

    @field:SerializedName("content")
    var content : String
)
