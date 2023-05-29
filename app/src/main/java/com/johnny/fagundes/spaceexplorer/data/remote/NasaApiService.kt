package com.johnny.fagundes.spaceexplorer.data.remote

import com.johnny.fagundes.spaceexplorer.BuildConfig
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    suspend fun getPicture(): PictureDayResponse

    @GET("mars-photos/api/v1/rovers/{roverName}/photos")
    suspend fun getMarsPhotos(
        @Path("roverName") roverName: String,
        @Query("sol") sol: Int = 1000,
        @Query("page") page: String
    ): MarsRoverPhotosResponse
}