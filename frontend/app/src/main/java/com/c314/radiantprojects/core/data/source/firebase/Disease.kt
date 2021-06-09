package com.c314.radiantprojects.core.data.source.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    val title: String,
    val description: String,
    val symptoms: String,
    val treatment: String,
    val image: String
) : Parcelable
