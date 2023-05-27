package com.johnny.fagundes.spaceexplorer.feature.marsphotos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.johnny.fagundes.spaceexplorer.databinding.FragmentMarsRoverPhotosBinding
import com.johnny.fagundes.spaceexplorer.feature.pictureday.PictureDayFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MarsRoverPhotosFragment : Fragment() {

    private lateinit var pictureDayJob: Job

    private var _binding: FragmentMarsRoverPhotosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarsRoverPhotosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsRoverPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservable()
        viewModel.fetchData()
    }

    private fun setupObservable() {
        pictureDayJob = lifecycleScope.launch {
            viewModel.marsPhotos.collect { state ->
                when (state) {
                    is MarsRoverPhotosViewModel.HomeUIState.Initial -> {
                        setupUI(true)
                    }
                    is MarsRoverPhotosViewModel.HomeUIState.Loading -> {
                        setupUI(true)
                    }
                    is MarsRoverPhotosViewModel.HomeUIState.Success -> {
                        Log.d(PictureDayFragment.TAG, "data is collected successfully.")
                        setupUI(false)
                    }
                    is MarsRoverPhotosViewModel.HomeUIState.Error -> {
                        Log.d(PictureDayFragment.TAG, "data is not collected, ERROR.")
                        setupError(state.error)
                    }
                }
            }
        }
    }

    private fun setupError(error: Throwable) {
        println(error)
    }

    private fun setupUI(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.constraintLayoutMarsPhotos.isVisible = !isLoading
    }

    companion object {
        const val TAG = "PICTURE_DAY_FRAGMENT"
    }

}
