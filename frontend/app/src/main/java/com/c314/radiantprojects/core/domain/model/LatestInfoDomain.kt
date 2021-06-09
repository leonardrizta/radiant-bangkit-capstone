package com.c314.radiantprojects.core.domain.model

import android.os.Parcelable


@kotlinx.parcelize.Parcelize
data class LatestInfoDomain(
    var id: Int,

    var author: String?,

    var title: String?,

    var description: String?,

    var urlImage: String?,

    var publishedAt: String?,

    var content: String?
) : Parcelable
