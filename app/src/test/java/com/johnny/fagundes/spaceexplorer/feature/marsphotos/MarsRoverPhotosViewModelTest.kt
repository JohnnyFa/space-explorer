package com.johnny.fagundes.spaceexplorer.feature.marsphotos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.domain.model.Camera
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhoto
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.domain.model.Rover
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MarsRoverPhotosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var repository = mockk<NasaRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify if when fetchData is called the return is Success`() = runTest {
        val marsPhotos = getMarsPhotos()

        val expectedState =
            MarsRoverPhotosViewModel.HomeUIState.Success(marsPhotos)

        val viewModel = instantiateViewModel()

        coEvery { repository.getMarsPhotos("curiosity", 1) } returns marsPhotos
        viewModel.fetchData()
        advanceUntilIdle()
        TestCase.assertEquals(expectedState, viewModel.marsPhotos.value)
    }

    @Test
    fun `verify if when fetchData is called with error, the return is Error`() = runTest {
        val expectedError = Exception("This is an expected error")

        val expectedState = MarsRoverPhotosViewModel.HomeUIState.Error(expectedError)

        val viewModel = instantiateViewModel()

        coEvery { repository.getMarsPhotos("curiosity", 1) } throws expectedError
        viewModel.fetchData()
        advanceUntilIdle()
        TestCase.assertEquals(expectedState, viewModel.marsPhotos.value)
    }

    private fun instantiateViewModel(): MarsRoverPhotosViewModel {
        return MarsRoverPhotosViewModel(repository)
    }

    private fun getMarsPhotos(): MarsRoverPhotosResponse {
        val camera = Camera(1, "Mast Camera", 1, "Mast Camera")
        val rover = Rover(1, "Curiosity", "2021-01-01", "2020-12-01", "Active")
        val photo =
            MarsRoverPhoto(1, 1000, camera, "https://example.com/photo.jpg", "2021-06-01", rover)
        return MarsRoverPhotosResponse(listOf(photo))
    }
}