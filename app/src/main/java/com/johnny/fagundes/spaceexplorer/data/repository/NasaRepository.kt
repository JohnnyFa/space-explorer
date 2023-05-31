package com.johnny.fagundes.spaceexplorer.data.repository

import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse

interface NasaRepository {
    suspend fun getPictureDay(): FactDayResponse

    suspend fun getMarsPhotos(rover: String, page: Int): MarsRoverPhotosResponse
}