package com.c314.radiantprojects.core.utils

import com.c314.radiantprojects.core.data.source.local.entity.articles.ArticlesEntity
import com.c314.radiantprojects.core.data.source.remote.response.Articles
import com.c314.radiantprojects.core.domain.model.ArticleDomain

object DataMapper {

    fun mapResponsesToEntities(input: List<Articles>): List<ArticlesEntity> {
        val articleList = ArrayList<ArticlesEntity>()
        input.map {
            val articles = ArticlesEntity(
                author = it.author,
                title = it.title,
                description = it.description,
                urlImage = it.urlImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
            articleList.add(articles)
        }
        return articleList
    }

    fun mapEntityToDomain(input: List<ArticlesEntity>): List<ArticleDomain> =
        input.map {
            ArticleDomain(
                id = it.id,
                author = it.author,
                title = it.title,
                description = it.description,
                urlImage = it.urlImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }

//    fun mapDomainToEntities(input:LatestInfoDomain) = LatestInfoEntity(
//        id = input.id,
//        author = input.author,
//        title = input.title,
//        description = input.description,
//        urlImage = input.urlImage,
//        publishedAt = input.publishedAt,
//        content = input.content
//    )


}