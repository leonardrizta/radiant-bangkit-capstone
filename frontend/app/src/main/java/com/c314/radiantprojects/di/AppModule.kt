package com.c314.radiantprojects.di

import com.c314.radiantprojects.core.domain.usecase.DataInteractor
import com.c314.radiantprojects.core.domain.usecase.DataUseCase
import com.c314.radiantprojects.ui.fragment.home.ContentViewModel
import com.c314.radiantprojects.ui.fragment.medicine.MedicineViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val useCaseModule = module {
        factory<DataUseCase> { DataInteractor(get()) }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    val viewModelModule = module {
        viewModel { ContentViewModel(get()) }
        viewModel { MedicineViewModel() }
//        viewModel { DetailViewModel(get()) }
//        viewModel { FavoriteMovieTvViewModel(get()) }
//        viewModel { SearchViewModel(get()) }
    }

}