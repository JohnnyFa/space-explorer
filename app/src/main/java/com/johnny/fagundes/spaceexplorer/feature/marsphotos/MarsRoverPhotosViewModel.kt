package com.johnny.fagundes.spaceexplorer.feature.marsphotos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhotosResponse
import com.johnny.fagundes.spaceexplorer.data.repository.NasaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarsRoverPhotosViewModel(private val nasaRepository: NasaRepository) : ViewModel() {

    private val _marsPhotosState: MutableStateFlow<HomeUIState> =
        MutableStateFlow(HomeUIState.Initial)
    val marsPhotos: StateFlow<HomeUIState> get() = _marsPhotosState

    fun fetchData() {
        _marsPhotosState.value = HomeUIState.Loading
        viewModelScope.launch {
            try {
                val mars = nasaRepository.getMarsPhotos("curiosity", 1)
                _marsPhotosState.value = HomeUIState.Success(mars)
            } catch (e: Exception) {
                _marsPhotosState.value = HomeUIState.Error(e)
            }
        }
    }

    sealed class HomeUIState {
        object Initial : HomeUIState()
        object Loading : HomeUIState()
        data class Success(val marsPhotos: MarsRoverPhotosResponse) : HomeUIState()
        data class Error(val error: Throwable) : HomeUIState()
    }
}