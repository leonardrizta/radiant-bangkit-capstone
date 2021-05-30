package com.c314.radiantprojects.core.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c314.radiantprojects.core.data.source.local.entity.articles.ArticlesEntity
import com.c314.radiantprojects.core.data.source.local.entity.news.LatestInfoEntity


@Database(
    entities = [ArticlesEntity::class],
    version =1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun appDao(): AppDao

}