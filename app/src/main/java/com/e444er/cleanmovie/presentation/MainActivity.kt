package com.e444er.cleanmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController

        binding.bottomBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            val isVisibleBottomBar = when (destination.id) {
                R.id.splashFragment -> false
                else -> true
            }

            binding.bottomBar.isVisible = isVisibleBottomBar
        }
    }
}