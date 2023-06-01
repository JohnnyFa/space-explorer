package com.johnny.fagundes.spaceexplorer.feature.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.johnny.fagundes.spaceexplorer.R

class LoadingScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val loadingAnimationView: LottieAnimationView
    private val loadingTextView: MaterialTextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading_screen, this, true)
        loadingAnimationView = findViewById(R.id.loadingAnimation)
        loadingTextView = findViewById(R.id.loadingText)
    }

    fun showLoading(animation: String) {
        visibility = View.VISIBLE
        val composition = LottieCompositionFactory.fromAssetSync(context, animation).value
        if (composition != null) {
            loadingAnimationView.setComposition(composition)
        }
        loadingAnimationView.playAnimation()
    }

    fun hideLoading() {
        visibility = View.GONE
        loadingAnimationView.pauseAnimation()
    }
}