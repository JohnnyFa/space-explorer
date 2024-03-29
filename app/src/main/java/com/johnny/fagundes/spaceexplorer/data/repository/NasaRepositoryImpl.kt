package com.johnny.fagundes.spaceexplorer.data.repository

import com.johnny.fagundes.spaceexplorer.data.remote.NasaApiService
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse

class NasaRepositoryImpl(
    private val nasaApiService: NasaApiService
) : NasaRepository {

    private val maxSol = 1000
    override suspend fun getPictureDay(): FactDayResponse {
        return nasaApiService.getPicture()
    }

    override suspend fun getMarsPhotos(rover: String, page: Int): MarsRoverPhotosResponse {
        return nasaApiService.getMarsPhotos(rover, maxSol, page.toString())
    }
}