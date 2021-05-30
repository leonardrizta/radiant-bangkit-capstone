package com.c314.radiantprojects.core.data.source.local

import androidx.lifecycle.LiveData
import com.c314.radiantprojects.core.data.source.local.db.AppDao
import com.c314.radiantprojects.core.data.source.local.entity.articles.ArticlesEntity
import com.c314.radiantprojects.core.data.source.local.entity.news.LatestInfoEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mAppDao: AppDao) {

    fun getArticles(): Flow<List<ArticlesEntity>> = mAppDao.getArticles()


    suspend fun insertArticles(articles: List<ArticlesEntity>) = mAppDao.insertArticles(articles)


}