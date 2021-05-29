package com.c314.radiantprojects.data.source.local.entity.news

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@kotlinx.parcelize.Parcelize
@Entity(tableName="articles")
data class LatestInfoEntity(
    @ColumnInfo(name="author")
    var author: String,

    @ColumnInfo(name="title")
    var title: String,

    @ColumnInfo(name="description")
    var description : String,

    @ColumnInfo(name="urlToImage")
    var urlImage : String,

    @ColumnInfo(name="publishedAt")
    var publishedAt : String,

    @ColumnInfo(name="content")
    var content : String
) : Parcelable
