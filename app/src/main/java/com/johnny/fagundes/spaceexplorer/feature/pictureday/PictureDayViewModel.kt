package com.johnny.fagundes.spaceexplorer.feature.pictureday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnny.fagundes.spaceexplorer.domain.model.PictureDayResponse
import com.johnny.fagundes.spaceexplorer.domain.repository.NasaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PictureDayViewModel(private val nasaRepository: NasaRepository) : ViewModel() {

    private val _pictureDayState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState.Initial)
    val pictureDayState: StateFlow<HomeUIState> get() = _pictureDayState

     fun fetchData() {
        _pictureDayState.value = HomeUIState.Loading
        viewModelScope.launch {
            try {
                val picture = nasaRepository.getPictureDay()
                _pictureDayState.value = HomeUIState.Success(picture)
            } catch (e: Exception) {
                _pictureDayState.value = HomeUIState.Error(e)
            }
        }
    }

    sealed class HomeUIState {
        object Initial : HomeUIState()
        object Loading : HomeUIState()
        data class Success(val picture: PictureDayResponse) : HomeUIState()
        data class Error(val error: Throwable) : HomeUIState()
    }
}