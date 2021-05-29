package com.c314.radiantprojects.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.c314.radiantprojects.data.source.local.entity.news.LatestInfoEntity

@Dao
interface AppDao {

    @Query("SELECT * FROM articles")
    fun getArticles(): LiveData<List<LatestInfoEntity>>

}