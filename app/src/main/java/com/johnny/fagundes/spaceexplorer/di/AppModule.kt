package com.johnny.fagundes.spaceexplorer.di

import com.johnny.fagundes.spaceexplorer.data.impl.NasaRepositoryImpl
import com.johnny.fagundes.spaceexplorer.domain.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.feature.MainViewModel
import com.johnny.fagundes.spaceexplorer.feature.marsphotos.MarsRoverPhotosViewModel
import com.johnny.fagundes.spaceexplorer.feature.pictureday.PictureDayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<NasaRepository> { NasaRepositoryImpl(get()) }

    viewModel { MainViewModel() }
    viewModel { PictureDayViewModel(get()) }
    viewModel { MarsRoverPhotosViewModel(get()) }
}