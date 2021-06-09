package com.c314.radiantprojects.core.data.repository

import com.c314.radiantprojects.core.data.source.local.LocalDataSource
import com.c314.radiantprojects.core.data.source.remote.RemoteDataSource
import com.c314.radiantprojects.core.data.source.remote.api.ApiResponse
import com.c314.radiantprojects.core.data.source.remote.response.LatestInfo
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.domain.repository.IDataRepository
import com.c314.radiantprojects.core.utils.AppExecutors
import com.c314.radiantprojects.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IDataRepository {

    override fun getLatestInfo(): Flow<Resource<List<LatestInfoDomain>>> =
        object : NetworkBoundResource<List<LatestInfoDomain>,List<LatestInfo>>(){
            override fun loadFromDB(): Flow<List<LatestInfoDomain>> {
                return localDataSource.getLatestInfo().map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<LatestInfoDomain>?): Boolean {
                return data == null || data.isEmpty()

            }

            override suspend fun createCall(): Flow<ApiResponse<List<LatestInfo>>> {
                return remoteDataSource.getLatestInfo()
            }

            override suspend fun saveCallResult(data: List<LatestInfo>) {
                val latestInfo = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertLatestInfo(latestInfo)
            }
        }.asFlow()






}