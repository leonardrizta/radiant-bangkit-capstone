package com.c314.radiantprojects.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class LatestInfoDomain(
    var id : Int,

    var author: String,

    var title: String,

    var description : String,

    var urlImage : String,

    var publishedAt : String,

    var content : String
)
