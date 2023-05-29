package com.johnny.fagundes.spaceexplorer.di

import com.johnny.fagundes.spaceexplorer.BuildConfig
import com.johnny.fagundes.spaceexplorer.data.network.ApiKeyInterceptor
import com.johnny.fagundes.spaceexplorer.data.network.LoggingInterceptor
import com.johnny.fagundes.spaceexplorer.data.remote.NasaApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    single { provideNasaApiService(get()) }
}

val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(ApiKeyInterceptor())
    .addInterceptor(LoggingInterceptor()) // Add your logging interceptor here
    .build()

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNasaApiService(retrofit: Retrofit): NasaApiService {
    return retrofit.create(NasaApiService::class.java)
}
