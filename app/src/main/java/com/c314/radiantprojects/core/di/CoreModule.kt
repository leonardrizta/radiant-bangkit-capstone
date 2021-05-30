package com.c314.radiantprojects.core.di

import androidx.room.Room
import com.c314.radiantprojects.BuildConfig
import com.c314.radiantprojects.core.data.repository.DataRepository
import com.c314.radiantprojects.core.data.source.local.LocalDataSource
import com.c314.radiantprojects.core.data.source.local.db.AppDatabase
import com.c314.radiantprojects.core.data.source.remote.RemoteDataSource
import com.c314.radiantprojects.core.data.source.remote.service.ApiService
import com.c314.radiantprojects.core.domain.repository.IDataRepository
import com.c314.radiantprojects.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {

    val databaseModule = module {
        factory { get<AppDatabase>().appDao() }
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java, "App.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    val networkModule = module {
        single {
            if (BuildConfig.DEBUG) {
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            } else {
                OkHttpClient
                    .Builder()
                    .build()
            }

        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<IDataRepository> { DataRepository(get(), get(), get()) }
    }

}