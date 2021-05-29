package com.c314.radiantprojects.data.source.local

import com.c314.radiantprojects.data.source.local.db.AppDao

class LocalDataSource(private val mAppDao: AppDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieAppDao: AppDao): LocalDataSource {
            if (instance == null) {
                instance = LocalDataSource(movieAppDao)
            }
            return instance as LocalDataSource
        }
    }






}