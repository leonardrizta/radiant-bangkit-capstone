package com.c314.radiantprojects.core.domain.usecase

import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.domain.repository.IDataRepository
import kotlinx.coroutines.flow.Flow

class DataInteractor(private val iDataRepository: IDataRepository) : DataUseCase {
    override fun getLatestInfo(): Flow<Resource<List<LatestInfoDomain>>> =
        iDataRepository.getLatestInfo()

}