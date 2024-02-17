package com.johnny.fagundes.spaceexplorer

import com.johnny.fagundes.spaceexplorer.data.impl.NasaRepositoryImplTest
import com.johnny.fagundes.spaceexplorer.ui.marsphotos.MarsRoverPhotosViewModelTest
import com.johnny.fagundes.spaceexplorer.ui.pictureday.PictureDayViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    NasaRepositoryImplTest::class,
    PictureDayViewModelTest::class,
    MarsRoverPhotosViewModelTest::class
)
class TestSuite