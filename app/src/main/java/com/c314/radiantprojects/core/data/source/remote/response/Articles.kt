package com.c314.radiantprojects.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("author")
    var author: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("description")
    var description : String?,

    @SerializedName("urlToImage")
    var urlImage : String?,

    @SerializedName("publishedAt")
    var publishedAt : String?,

    @SerializedName("content")
    var content : String?

)
