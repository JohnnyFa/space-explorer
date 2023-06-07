package com.johnny.fagundes.spaceexplorer.feature.marsphotos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnny.fagundes.spaceexplorer.R
import com.johnny.fagundes.spaceexplorer.data.remote.RetryCallback
import com.johnny.fagundes.spaceexplorer.databinding.FragmentMarsRoverPhotosBinding
import com.johnny.fagundes.spaceexplorer.domain.model.MarsRoverPhoto
import com.johnny.fagundes.spaceexplorer.feature.error.ErrorFragment
import com.johnny.fagundes.spaceexplorer.feature.marsphotos.adapter.MarsPhotosAdapter
import com.johnny.fagundes.spaceexplorer.feature.pictureday.PictureDayFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MarsRoverPhotosFragment : Fragment(), RetryCallback {

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
        setupRecyclerView()
        viewModel.fetchData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservable() {
        pictureDayJob = lifecycleScope.launch {
            viewModel.marsPhotos.collect { state ->
                when (state) {
                    is MarsRoverPhotosViewModel.HomeUIState.Initial -> {
                        isLoading(true)
                    }

                    is MarsRoverPhotosViewModel.HomeUIState.Loading -> {
                        isLoading(true)
                    }

                    is MarsRoverPhotosViewModel.HomeUIState.Success -> {
                        Timber.tag(PictureDayFragment.TAG).d("data is collected successfully.")
                        setupMarsPictures(state.marsPhotos.photos)
                        isLoading(false)
                    }

                    is MarsRoverPhotosViewModel.HomeUIState.Error -> {
                        Timber.tag(PictureDayFragment.TAG).d("data is not collected, ERROR.")
                        showError(state.error)
                    }
                }
            }
        }
    }

    private fun showError(error: Throwable) {
        println(error.message)
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val errorFragment = ErrorFragment()

        fragmentTransaction.replace(R.id.errorFragment, errorFragment)
        fragmentTransaction.commit()
    }

    private fun isLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.constraintLayoutMarsPhotos.isVisible = !isLoading
    }

    private fun setupMarsPictures(marsPhotos: List<MarsRoverPhoto>) {
        val adapter = MarsPhotosAdapter(marsPhotos)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        const val TAG = "PICTURE_DAY_FRAGMENT"
    }

    override fun retry() {
        viewModel.fetchData()
    }

}
