package com.c314.radiantprojects.core.data.source.remote


import com.c314.radiantprojects.BuildConfig
import com.c314.radiantprojects.core.data.source.remote.api.ApiResponse
import com.c314.radiantprojects.core.data.source.remote.response.Articles
import com.c314.radiantprojects.core.data.source.remote.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    private val apiKey = BuildConfig.LATEST_INFO_API_KEY


    suspend fun getLatestInfo(): Flow<ApiResponse<List<Articles>>> {
        return flow {
            try {
                val response = apiService.getArticles(apiKey)
                val latestInfo = response.articles
                if (latestInfo.isNotEmpty()) {
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}