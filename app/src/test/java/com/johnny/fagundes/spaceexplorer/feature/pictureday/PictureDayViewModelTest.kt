package com.johnny.fagundes.spaceexplorer.feature.pictureday

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.feature.pictureday.PictureDayViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PictureDayViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private var repository = mockk<NasaRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `verify if when fetchData is called the return is Success`() {
        val picture = getMockedPicture()

        val expectedState =
            PictureDayViewModel.HomeUIState.Success(picture)

        val viewModel = instantiateViewModel()

        coEvery { repository.getPictureDay() } returns picture
        viewModel.fetchData()

        assertEquals(expectedState, viewModel.pictureDayState.value)
    }

    @Test
    fun `verify every step of the function when it's being called`() {
        val picture = getMockedPicture()
        val initState = PictureDayViewModel.HomeUIState.Initial
        val loadingState = PictureDayViewModel.HomeUIState.Loading
        val successState = PictureDayViewModel.HomeUIState.Success(picture)

        val viewModel = instantiateViewModel()

        //check init state
        assertEquals(initState, viewModel.pictureDayState.value)

        coEvery { repository.getPictureDay() } coAnswers {
            delay(500)
            picture
        }

        viewModel.fetchData()
        //after the call check if init state is loading
        assertEquals(loadingState, viewModel.pictureDayState.value)
        testDispatcher.advanceTimeBy(500)
        //after that checks if init state is success
        assertEquals(successState, viewModel.pictureDayState.value)

    }

    private fun instantiateViewModel(): PictureDayViewModel {
        return PictureDayViewModel(repository)
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
