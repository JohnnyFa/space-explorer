package com.johnny.fagundes.spaceexplorer.feature.pictureday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.utils.sharedprefs.NasaSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PictureDayViewModel(
    private val nasaRepository: NasaRepository,
    private val nasaSharedPreferences: NasaSharedPreferences
) : ViewModel() {

    private val _pictureDayState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState.Initial)
    val pictureDayState: StateFlow<HomeUIState> get() = _pictureDayState


    fun checkIfPictureIsSaved() {
        val savedPicData = getSavedPicture()
        savedPicData?.let {
            if (savedPicData.isToday()) {
                _pictureDayState.tryEmit(HomeUIState.Success(savedPicData))
                return
            } else fetchData()
        } ?: fetchData()
    }

    fun fetchData() {
        _pictureDayState.tryEmit(HomeUIState.Loading)

        viewModelScope.launch {
            try {
                val picture = nasaRepository.getPictureDay()
                savePicture(picture)
                _pictureDayState.tryEmit(HomeUIState.Success(picture))
            } catch (e: Exception) {
                _pictureDayState.tryEmit(HomeUIState.Error(e))
            }
        }
    }

    private fun getSavedPicture(): FactDayResponse? {
        return nasaSharedPreferences.getSavedPicture()
    }

    private fun savePicture(picture: FactDayResponse) {
        nasaSharedPreferences.savePicture(picture)
    }

    sealed class HomeUIState {
        object Initial : HomeUIState()
        object Loading : HomeUIState()
        data class Success(val picture: FactDayResponse) : HomeUIState()
        data class Error(val error: Throwable) : HomeUIState()
    }
}