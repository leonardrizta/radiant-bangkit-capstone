package com.c314.radiantprojects.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c314.radiantprojects.core.data.repository.DataRepository
import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.ArticleDomain
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.domain.usecase.DataUseCase

class ContentViewModel(private val dataUseCase: DataUseCase) : ViewModel() {
//    fun getArticles(): List<LatestInfoEntity> = DataDummy.generateArticles()

    fun getArticles(): LiveData<Resource<List<ArticleDomain>>> =
        dataUseCase.getArticles().asLiveData()


}