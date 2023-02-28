package com.e444er.cleanmovie

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.e444er.cleanmovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    @Inject
//    lateinit var getUIModeUseCase: GetUIModeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                getUIModeUseCase().collectLatest { uiMode ->
//                    if (uiMode == AppCompatDelegate.MODE_NIGHT_YES) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    } else {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    }
//                }
//            }
//        }

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController

        binding.bottomBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            val isVisibleBottomBar = when (destination.id) {
                R.id.homeFragment -> true
                R.id.exploreFragment -> true
                R.id.listFragment -> true
                R.id.settingsFragment -> true
                else -> false
            }

            binding.bottomBar.isVisible = isVisibleBottomBar
        }
    }
}