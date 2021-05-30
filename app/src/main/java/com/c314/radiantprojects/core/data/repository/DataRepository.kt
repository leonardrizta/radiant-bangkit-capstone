package com.c314.radiantprojects.core.data.repository

import com.c314.radiantprojects.core.data.source.local.LocalDataSource
import com.c314.radiantprojects.core.data.source.remote.RemoteDataSource
import com.c314.radiantprojects.core.data.source.remote.api.ApiResponse
import com.c314.radiantprojects.core.data.source.remote.response.Articles
import com.c314.radiantprojects.core.domain.model.ArticleDomain
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.domain.repository.IDataRepository
import com.c314.radiantprojects.core.utils.AppExecutors
import com.c314.radiantprojects.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IDataRepository {

    override fun getArticles(): Flow<Resource<List<ArticleDomain>>> =
        object : NetworkBoundResource<List<ArticleDomain>, List<Articles>>() {
            override fun loadFromDB(): Flow<List<ArticleDomain>> {
                return localDataSource.getArticles().map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<ArticleDomain>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<Articles>>> {
                return remoteDataSource.getLatestInfo()
            }

            override suspend fun saveCallResult(data: List<Articles>) {
                val latestInfo = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertArticles(latestInfo)
            }
        }.asFlow()


}