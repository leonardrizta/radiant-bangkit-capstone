package com.c314.radiantprojects.core.domain.repository

import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    fun getLatestInfo() : Flow<Resource<List<LatestInfoDomain>>>
}