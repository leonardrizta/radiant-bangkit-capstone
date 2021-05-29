package com.c314.radiantprojects.ui.fragment.home

import androidx.lifecycle.ViewModel
import com.c314.radiantprojects.data.source.local.entity.news.LatestInfoEntity
import com.c314.radiantprojects.utils.DataDummy

class ContentViewModel : ViewModel() {
    fun getArticles(): List<LatestInfoEntity> = DataDummy.generateArticles()
}