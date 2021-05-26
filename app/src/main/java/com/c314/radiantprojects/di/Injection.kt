package com.c314.radiantprojects.di

import android.content.Context
import com.c314.radiantprojects.data.repository.DataRepository
import com.c314.radiantprojects.data.source.local.LocalDataSource
import com.c314.radiantprojects.data.source.local.db.AppDatabase
import com.c314.radiantprojects.data.source.remote.RemoteDataSource
import com.c314.radiantprojects.utils.AppExecutors

object Injection {

//    fun appRepository(context: Context): DataRepository {
//
//        val database = AppDatabase.getInstance(context)
//        val remoteRepository = RemoteDataSource.getInstance()
//        val localRepository = LocalDataSource.getInstance(database.appDao())
//        val appExecutors = AppExecutors()
//
//        return DataRepository.getInstance(remoteRepository, localRepository, appExecutors)
//    }

}