package com.johnny.fagundes.spaceexplorer.data.impl

import com.johnny.fagundes.spaceexplorer.data.remote.NasaApiService
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse
import com.johnny.fagundes.spaceexplorer.domain.repository.NasaRepository

class NasaRepositoryImpl(private val nasaApiService: NasaApiService) : NasaRepository {
    override suspend fun getPictureDay(): PictureDayResponse {
        return nasaApiService.getPicture()
    }

    override suspend fun getMarsPhotos(rover: String, page: Int): MarsRoverPhotosResponse {
        return nasaApiService.getMarsPhotos(rover, page.toString())
    }
}