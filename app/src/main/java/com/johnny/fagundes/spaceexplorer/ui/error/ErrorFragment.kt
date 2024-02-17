package com.johnny.fagundes.spaceexplorer.ui.error

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnny.fagundes.spaceexplorer.data.remote.RetryCallback
import com.johnny.fagundes.spaceexplorer.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {
    private var retryCallback: RetryCallback? = null

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
    }

    private fun setupButton() {
        binding.btnRetry.setOnClickListener {
            println("retry callback is null: $retryCallback")
            retryCallback?.retry()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is RetryCallback) {
            retryCallback = parentFragment as RetryCallback
        }
    }
}