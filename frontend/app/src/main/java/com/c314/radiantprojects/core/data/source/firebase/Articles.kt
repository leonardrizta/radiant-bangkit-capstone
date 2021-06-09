package com.c314.radiantprojects.core.data.source.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Articles(
    val date: String,
    val desc: String,
    val title: String,
    val url_img: String
) : Parcelable
