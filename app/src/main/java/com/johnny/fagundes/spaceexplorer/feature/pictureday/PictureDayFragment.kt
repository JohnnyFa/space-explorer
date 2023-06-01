package com.johnny.fagundes.spaceexplorer.feature.pictureday

import android.media.session.MediaController
import android.net.Uri
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.johnny.fagundes.spaceexplorer.R
import com.johnny.fagundes.spaceexplorer.data.remote.RetryCallback
import com.johnny.fagundes.spaceexplorer.databinding.FragmentPictureDayBinding
import com.johnny.fagundes.spaceexplorer.domain.model.FactDayResponse
import com.johnny.fagundes.spaceexplorer.feature.error.ErrorFragment
import com.johnny.fagundes.spaceexplorer.utils.ImageUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PictureDayFragment : Fragment(), RetryCallback {

    private lateinit var pictureDayJob: Job

    private var _binding: FragmentPictureDayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureDayViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.checkIfPictureIsSaved()
    }

    private fun setupObserver() {
        pictureDayJob = lifecycleScope.launch {
            viewModel.pictureDayState.collect { state ->
                when (state) {
                    is PictureDayViewModel.HomeUIState.Initial -> {
                        setupUI(true)
                    }

                    is PictureDayViewModel.HomeUIState.Loading -> {
                        setupUI(true)
                    }

                    is PictureDayViewModel.HomeUIState.Success -> {
                        Timber.tag(TAG).d("data is collected successfully.")
                        setUrlOfTheDay(state.picture)
                        setupUI(false)
                    }

                    is PictureDayViewModel.HomeUIState.Error -> {
                        Timber.tag(TAG).d("data is not collected, ERROR.")
                        showError(state.error)
                        setupUI(false)
                    }
                }
            }
        }
    }

    private fun setUrlOfTheDay(item: FactDayResponse) {
        when (item.mediaType()) {
            FactDayResponse.IMAGE -> setPictureDay(item.url)
            FactDayResponse.VIDEO -> setVideoDay(item.url)
        }
        setText(item.title, item.explanation)
    }

    private fun showError(error: Throwable) {
        println(error.message)
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val errorFragment = ErrorFragment()

        fragmentTransaction.replace(R.id.errorFragment, errorFragment)
        fragmentTransaction.commit()
    }

    private fun setupUI(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingScreen.showLoading("space_man_white.json")
        } else {
            binding.loadingScreen.hideLoading()
        }
        binding.loadingScreen.isVisible = isLoading
        binding.constraintLayoutPictureDay.isVisible = !isLoading
    }

    private fun setPictureDay(picture: String) {
        ImageUtils.loadImage(
            requireContext(), picture, binding.imageView
        )
        binding.webView.isVisible = false
    }

    private fun setVideoDay(video: String) {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(video)
        binding.imageView.isVisible = false
    }

    private fun setText(title: String, explanation: String) {
        binding.textViewTitle.text = title
        binding.textViewExplanation.text = explanation
    }

    override fun retry() {
        viewModel.fetchData()
        val fragmentManager = childFragmentManager
        val errorFragment = fragmentManager.findFragmentById(R.id.errorFragment)
        errorFragment?.let {
            fragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pictureDayJob.cancel()
        _binding = null
    }

    companion object {
        const val TAG = "PICTURE_DAY_FRAGMENT"
    }
}