package com.c314.radiantprojects.core.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c314.radiantprojects.core.data.source.local.entity.LatestInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM latest_info")
    fun getLatestInfo(): Flow<List<LatestInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestInfo(articles: List<LatestInfoEntity>)

}