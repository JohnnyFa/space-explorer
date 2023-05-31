package com.johnny.fagundes.spaceexplorer.feature.pictureday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import com.johnny.fagundes.spaceexplorer.feature.utils.sharedprefs.NasaSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PictureDayViewModel(private val nasaRepository: NasaRepository, private val nasaSharedPreferences: NasaSharedPreferences) : ViewModel() {

    private val _pictureDayState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState.Initial)
    val pictureDayState: StateFlow<HomeUIState> get() = _pictureDayState

    fun fetchData() {
        val savedPicData = getSavedPicture()
        savedPicData?.let {
            if (!savedPicData.isToday()) {
                _pictureDayState.value = HomeUIState.Success(savedPicData)
                return
            }
        }

        _pictureDayState.value = HomeUIState.Loading
        viewModelScope.launch {
            try {
                val picture = nasaRepository.getPictureDay()
                savePicture(picture)
                _pictureDayState.value = HomeUIState.Success(picture)
            } catch (e: Exception) {
                _pictureDayState.value = HomeUIState.Error(e)
            }
        }
    }

    private fun getSavedPicture(): PictureDayResponse? {
        return nasaSharedPreferences.getSavedPicture()
    }

    private fun savePicture(picture: PictureDayResponse) {
        nasaSharedPreferences.savePicture(picture)
    }

    sealed class HomeUIState {
        object Initial : HomeUIState()
        object Loading : HomeUIState()
        data class Success(val picture: PictureDayResponse) : HomeUIState()
        data class Error(val error: Throwable) : HomeUIState()
    }
}