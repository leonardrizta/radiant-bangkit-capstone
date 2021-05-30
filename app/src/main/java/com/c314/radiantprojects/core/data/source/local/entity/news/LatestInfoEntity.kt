package com.c314.radiantprojects.core.data.source.local.entity.news

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@kotlinx.parcelize.Parcelize
@Entity(tableName="latest_info")
data class LatestInfoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Int = 0,

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
