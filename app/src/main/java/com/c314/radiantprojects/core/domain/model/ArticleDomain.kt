package com.c314.radiantprojects.core.domain.model

data class ArticleDomain(
    var id : Int,

    var author: String?,

    var title: String?,

    var description : String?,

    var urlImage : String?,

    var publishedAt : String?,

    var content : String?
)
