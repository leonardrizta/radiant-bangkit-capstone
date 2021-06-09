package com.c314.radiantprojects.core.data.source.local

import com.c314.radiantprojects.core.data.source.local.db.AppDao
import com.c314.radiantprojects.core.data.source.local.entity.LatestInfoEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mAppDao: AppDao) {

    fun getLatestInfo(): Flow<List<LatestInfoEntity>> = mAppDao.getLatestInfo()


    suspend fun insertLatestInfo(latestInfo: List<LatestInfoEntity>) =
        mAppDao.insertLatestInfo(latestInfo)


}