package com.c314.radiantprojects.utils

import com.c314.radiantprojects.core.data.source.local.entity.news.LatestInfoEntity

object DataDummy {

    fun generateArticles(): List<LatestInfoEntity> {
        return listOf(
            LatestInfoEntity(
                0,
                "",
                "",
                "https://foto.kontan.co.id/O6i6QJfzxJmSwqX-dZFl8305_2c=/smart/2021/01/18/446341773p.jpg",
                "",
                "",
                ""
            ),
            LatestInfoEntity(
                0,
                "",
                "",
                "https://photo.jpnn.com/arsip/watermark/2020/11/03/ilustrasi-logo-diabetes-foto-antara-66.png",
                "",
                "",
                ""
            ),
            LatestInfoEntity(
                0,
                "",
                "",
                "https://pict-b.sindonews.net/dyn/620/pena/news/2021/05/26/155/437578/5-jenis-olahraga-yang-bisa-meredakan-peradangan-yfz.jpg",
                "",
                "",
                ""
            ),
        )
    }

}