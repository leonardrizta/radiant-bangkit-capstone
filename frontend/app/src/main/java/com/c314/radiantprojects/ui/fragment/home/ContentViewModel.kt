package com.c314.radiantprojects.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.domain.usecase.DataUseCase

import com.c314.radiantprojects.utils.DataDummy

class ContentViewModel(private val dataUseCase: DataUseCase) : ViewModel() {
//    fun getArticles(): List<LatestInfoEntity> = DataDummy.generateArticles()

    fun getLatestInfo(): LiveData<Resource<List<LatestInfoDomain>>> =
        dataUseCase.getLatestInfo().asLiveData()


}