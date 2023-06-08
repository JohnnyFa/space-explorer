package com.johnny.fagundes.spaceexplorer.feature.pictureday

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.utils.sharedprefs.NasaSharedPreferences
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class PictureDayViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var repository = mockk<NasaRepository>()
    private var nasaSharedPreferences = mockk<NasaSharedPreferences>()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk()
        nasaSharedPreferences = mockk()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify if when fetchData is called the return is Success`() = runTest {
        val picture = getMockedValueOfToday()

        val expectedState =
            PictureDayViewModel.HomeUIState.Success(picture)

        val viewModel = instantiateViewModel()

        coEvery { nasaSharedPreferences.savePicture(getMockedValueOfToday()) } returns Unit
        coEvery { repository.getPictureDay() } returns picture
        viewModel.fetchData()
        advanceUntilIdle()
        assertEquals(expectedState, viewModel.pictureDayState.value)
    }

    @Test
    fun `verify if when fetchData is called with error, the return is Error`() = runTest {
        val errorExpected = Exception("This is an expected error")

        val expectedState =
            PictureDayViewModel.HomeUIState.Error(errorExpected)

        val viewModel = instantiateViewModel()

        coEvery { nasaSharedPreferences.savePicture(getMockedValueOfToday()) } returns Unit
        coEvery { repository.getPictureDay() } throws errorExpected
        viewModel.fetchData()
        advanceUntilIdle()
        assertEquals(expectedState, viewModel.pictureDayState.value)
    }

    @Test
    fun `verify every step of the function when it's being called`() = runTest {
        val picture = getMockedValueOfToday()
        val initState = PictureDayViewModel.HomeUIState.Initial
        val loadingState = PictureDayViewModel.HomeUIState.Loading
        val successState = PictureDayViewModel.HomeUIState.Success(picture)

        val viewModel = instantiateViewModel()

        //check init state
        assertEquals(initState, viewModel.pictureDayState.value)

        coEvery { nasaSharedPreferences.savePicture(getMockedValueOfToday()) } returns Unit
        coEvery { repository.getPictureDay() } coAnswers {
            delay(500)
            picture
        }

        viewModel.fetchData()
        //after the call check if init state is loading
        assertEquals(loadingState, viewModel.pictureDayState.value)
        advanceUntilIdle()
        //after that checks if init state is success
        assertEquals(successState, viewModel.pictureDayState.value)

    }

    @Test
    fun `verify if the pic is saved if it is it will return success`() {
        val successState = PictureDayViewModel.HomeUIState.Success(getMockedValueOfToday())

        val viewModel = instantiateViewModel()

        coEvery { nasaSharedPreferences.getSavedPicture() } returns getMockedValueOfToday()

        viewModel.checkIfPictureIsSaved()

        assertEquals(successState, viewModel.pictureDayState.value)
    }

    @Test
    fun `verify if the data is saved, if it is, it will check the date and see that it's not today so fetchData will be called`() =
        runTest {
            val successState = PictureDayViewModel.HomeUIState.Success(getMockedValueOfToday())

            val viewModel = instantiateViewModel()

            coEvery { nasaSharedPreferences.getSavedPicture() } returns getMockedValueOfBeforeToday()
            coEvery { nasaSharedPreferences.savePicture(getMockedValueOfToday()) } returns Unit
            coEvery { repository.getPictureDay() } returns getMockedValueOfToday()

            viewModel.checkIfPictureIsSaved()
            advanceUntilIdle()

            assertEquals(successState, viewModel.pictureDayState.value)
        }

    @Test
    fun `verify if the data saved is null, if it is, it will called fetchData`() = runTest {
        val nullData = getNullMocked()
        val successState = PictureDayViewModel.HomeUIState.Success(getMockedValueOfToday())

        val viewModel = instantiateViewModel()

        coEvery { nasaSharedPreferences.getSavedPicture() } returns nullData
        coEvery { nasaSharedPreferences.savePicture(getMockedValueOfToday()) } returns Unit
        coEvery { repository.getPictureDay() } returns getMockedValueOfToday()

        viewModel.checkIfPictureIsSaved()
        advanceUntilIdle()
        assertEquals(successState, viewModel.pictureDayState.value)
    }

    private fun instantiateViewModel(): PictureDayViewModel {
        return PictureDayViewModel(repository, nasaSharedPreferences)
    }

    private fun getMockedValueOfToday(): FactDayResponse {
        return FactDayResponse(
            date = LocalDate.now().toString(),
            explanation = "Sample explanation",
            media_type = "image",
            title = "Sample title",
            url = "https://example.com/image.jpg"
        )
    }

    private fun getMockedValueOfBeforeToday(): FactDayResponse {
        return FactDayResponse(
            date = "2023-05-27",
            explanation = "Sample explanation",
            media_type = "image",
            title = "Sample title",
            url = "https://example.com/image.jpg"
        )
    }

    private fun getNullMocked(): FactDayResponse? {
        return null
    }

}
