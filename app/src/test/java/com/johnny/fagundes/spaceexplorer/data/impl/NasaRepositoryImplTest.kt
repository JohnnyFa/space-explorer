package com.johnny.fagundes.spaceexplorer.data.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnny.fagundes.spaceexplorer.data.remote.NasaApiService
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepositoryImpl
import com.johnny.fagundes.spaceexplorer.domain.model.Camera
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhoto
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.Rover
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NasaRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var nasaApiService: NasaApiService = mockk()

    @Test
    fun `when getPictureDay is called it should call service getPicture`() {
        coEvery { nasaApiService.getPicture() } returns getMockedPicture()

        runBlocking {
            NasaRepositoryImpl(nasaApiService).getPictureDay()
        }

        coEvery {
            nasaApiService.getPicture()
        }

    }

    @Test
    fun `when getMarsPhotos is called it should call service getMarsPhotos`(){
        coEvery { nasaApiService.getMarsPhotos("curiosity", 1000,"1") } returns getMarsPhotos()

        runBlocking {
            NasaRepositoryImpl(nasaApiService).getMarsPhotos("curiosity", 1)
        }

        coEvery {
            nasaApiService.getMarsPhotos("curiosity", 1000,"1")
        }
    }

    private fun getMockedPicture(): FactDayResponse {
        return FactDayResponse(
            date = "2023-05-27",
            explanation = "Sample explanation",
            media_type = "image",
            title = "Sample title",
            url = "https://example.com/image.jpg"
        )
    }

    private fun getMarsPhotos(): MarsRoverPhotosResponse {
        val camera = Camera(1, "Mast Camera", 1, "Mast Camera")
        val rover = Rover(1, "Curiosity", "2021-01-01", "2020-12-01", "Active")
        val photo = MarsRoverPhoto(1, 1000, camera, "https://example.com/photo.jpg", "2021-06-01", rover)
        return MarsRoverPhotosResponse(listOf(photo))
    }
}