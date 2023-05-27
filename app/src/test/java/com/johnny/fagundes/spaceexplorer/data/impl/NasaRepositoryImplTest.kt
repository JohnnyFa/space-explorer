package com.johnny.fagundes.spaceexplorer.data.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnny.fagundes.spaceexplorer.data.remote.NasaApiService
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NasaRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
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

    private fun getMockedPicture(): PictureDayResponse {
        return PictureDayResponse(
            date = "2023-05-27",
            explanation = "Sample explanation",
            media_type = "image",
            title = "Sample title",
            url = "https://example.com/image.jpg"
        )
    }
}