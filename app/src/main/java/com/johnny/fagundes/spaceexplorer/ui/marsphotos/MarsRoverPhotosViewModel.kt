package com.johnny.fagundes.spaceexplorer.ui.marsphotos

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
        _marsPhotosState.tryEmit(HomeUIState.Loading)
        viewModelScope.launch {
            try {
                val mars = nasaRepository.getMarsPhotos("curiosity", 1)
                _marsPhotosState.tryEmit(HomeUIState.Success(mars))
            } catch (e: Exception) {
                _marsPhotosState.tryEmit(HomeUIState.Error(e))
            }
        }
    }

    sealed class HomeUIState {
        data object Initial : HomeUIState()
        data object Loading : HomeUIState()
        data class Success(val marsPhotos: MarsRoverPhotosResponse) : HomeUIState()
        data class Error(val error: Throwable) : HomeUIState()
    }
}