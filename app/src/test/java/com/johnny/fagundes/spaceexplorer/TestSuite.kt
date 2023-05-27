package com.johnny.fagundes.spaceexplorer

import com.johnny.fagundes.spaceexplorer.data.impl.NasaRepositoryImplTest
import com.johnny.fagundes.spaceexplorer.feature.pictureday.PictureDayViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    NasaRepositoryImplTest::class,
    PictureDayViewModelTest::class
)
class TestSuite