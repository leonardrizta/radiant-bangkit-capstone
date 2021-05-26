package com.c314.radiantprojects.data.source.remote


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c314.radiantprojects.BuildConfig
import com.c314.radiantprojects.data.source.remote.api.ApiResponse
import com.c314.radiantprojects.data.source.remote.response.LatestInfo
import com.c314.radiantprojects.data.source.remote.response.LatestInfoResponse
import com.c314.radiantprojects.data.source.remote.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(){
    private val apiKey = BuildConfig.LATEST_INFO_API_KEY
    private val mConfig = ApiConfig

    companion object {
        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getArticles(): LiveData<ApiResponse<List<LatestInfo>>> {
        val resultArticles = MutableLiveData<ApiResponse<List<LatestInfo>>>()
        mConfig.getApiService().getLatestInfo(apiKey)
            .enqueue(object : Callback<LatestInfoResponse> {
                override fun onFailure(call: Call<LatestInfoResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(
                    call: Call<LatestInfoResponse>,
                    response: Response<LatestInfoResponse>
                ) {
                    resultArticles.value =
                        ApiResponse.success(response.body()?.articles as List<LatestInfo>)
                }
            })

        return resultArticles
    }

}