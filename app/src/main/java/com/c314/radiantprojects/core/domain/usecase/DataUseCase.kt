package com.c314.radiantprojects.core.domain.usecase

import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.ArticleDomain
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import kotlinx.coroutines.flow.Flow

interface DataUseCase {

    fun getArticles() : Flow<Resource<List<ArticleDomain>>>
}