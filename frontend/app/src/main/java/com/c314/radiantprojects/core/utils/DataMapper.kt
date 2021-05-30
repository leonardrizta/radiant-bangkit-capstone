package com.c314.radiantprojects.core.utils

import com.c314.radiantprojects.core.data.source.local.entity.LatestInfoEntity
import com.c314.radiantprojects.core.data.source.remote.response.LatestInfo
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain

object DataMapper {

    fun mapResponsesToEntities(input: List<LatestInfo>): List<LatestInfoEntity> {
        val latestInfoList = ArrayList<LatestInfoEntity>()
        input.map {
            val latestInfo = LatestInfoEntity(
                author = it.author,
                title = it.title,
                description = it.description,
                urlImage = it.urlImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
            latestInfoList.add(latestInfo)
        }
        return latestInfoList
    }

    fun mapEntityToDomain(input: List<LatestInfoEntity>): List<LatestInfoDomain> =
        input.map {
            LatestInfoDomain(
                id = it.id,
                author = it.author,
                title = it.title,
                description = it.description,
                urlImage = it.urlImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }


}