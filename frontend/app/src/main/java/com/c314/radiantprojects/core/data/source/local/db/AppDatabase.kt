package com.c314.radiantprojects.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.c314.radiantprojects.core.data.source.local.entity.LatestInfoEntity

@Database(
    entities = [LatestInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}