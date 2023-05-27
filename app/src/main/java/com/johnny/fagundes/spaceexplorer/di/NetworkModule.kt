package com.johnny.fagundes.spaceexplorer.di

import com.johnny.fagundes.spaceexplorer.BuildConfig
import com.johnny.fagundes.spaceexplorer.data.impl.NasaRepositoryImpl
import com.johnny.fagundes.spaceexplorer.data.remote.NasaApiService
import com.johnny.fagundes.spaceexplorer.domain.repository.NasaRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    single { provideNasaApiService(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNasaApiService(retrofit: Retrofit): NasaApiService {
    return retrofit.create(NasaApiService::class.java)
}
