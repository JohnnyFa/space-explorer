package com.johnny.fagundes.spaceexplorer.di

import android.content.Context
import com.google.gson.Gson
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepositoryImpl
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.feature.MainViewModel
import com.johnny.fagundes.spaceexplorer.feature.marsphotos.MarsRoverPhotosViewModel
import com.johnny.fagundes.spaceexplorer.feature.pictureday.PictureDayViewModel
import com.johnny.fagundes.spaceexplorer.feature.utils.sharedprefs.BaseSharedPreferences
import com.johnny.fagundes.spaceexplorer.feature.utils.sharedprefs.NasaSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<NasaRepository> { NasaRepositoryImpl(get()) }

    single {
        androidContext().getSharedPreferences(
            "Space_explorer_preferences",
            Context.MODE_PRIVATE
        )
    }

    single { Gson() }

    single<BaseSharedPreferences> {
        NasaSharedPreferences(
            sharedPreferences = get(),
            gson = get()
        )
    }

    single { NasaSharedPreferences(get(), get()) }

    viewModel { MainViewModel() }
    viewModel { PictureDayViewModel(get(), get<NasaSharedPreferences>()) }
    viewModel { MarsRoverPhotosViewModel(get()) }
}