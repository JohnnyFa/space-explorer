package com.johnny.fagundes.spaceexplorer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.johnny.fagundes.spaceexplorer.R
import com.johnny.fagundes.spaceexplorer.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        setupNavBar()
    }

    private fun setupNavBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        if (navHostFragment is NavHostFragment) {
            val navController = navHostFragment.navController
            binding.bottomNavigationView.setupWithNavController(navController)

            binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_earth -> {
                        navController.navigate(R.id.earthFragment)
                        true
                    }
                    R.id.action_mars -> {
                        navController.navigate(R.id.marsFragment)
                        true
                    }
                    R.id.action_picture_of_day -> {
                        navController.navigate(R.id.pictureOfDayFragment)
                        true
                    }
                    R.id.action_asteroids -> {
                        navController.navigate(R.id.asteroidsFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }
}