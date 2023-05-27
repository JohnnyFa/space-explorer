package com.johnny.fagundes.spaceexplorer.domain.repository

import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse

interface NasaRepository {
    suspend fun getPictureDay(): PictureDayResponse

    suspend fun getMarsPhotos(rover: String, page: Int): MarsRoverPhotosResponse
}