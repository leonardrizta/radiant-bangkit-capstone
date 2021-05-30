package com.c314.radiantprojects.core.data.source.local.entity.articles

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName="articles")
data class ArticlesEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Int = 0,

    @ColumnInfo(name="author")
    var author: String?,

    @ColumnInfo(name="title")
    var title: String?,

    @ColumnInfo(name="description")
    var description : String?,

    @ColumnInfo(name="urlToImage")
    var urlImage : String?,

    @ColumnInfo(name="publishedAt")
    var publishedAt : String?,

    @ColumnInfo(name="content")
    var content : String?

    ) : Parcelable
