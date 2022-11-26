package com.anastasiaiu.dttrealestate.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.ActivityRealEstateAppBinding

class RealEstateAppActivity : AppCompatActivity() {

    private var _binding: ActivityRealEstateAppBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the splash screen theme
        setTheme(R.style.Theme_App_SplashScreen)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )

        super.onCreate(savedInstanceState)

        // Set the main app theme
        setTheme(R.style.Theme_DTTRealEstate)

        _binding = ActivityRealEstateAppBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        // TODO: Fix description
        // Fix status bar background color after the splash screen. Set colors for supporting dark theme.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {

            // Set the background color of the activity to black.
            // Fixes the issue with white icons at the status bar.
            binding.root.setBackgroundColor(
                ContextCompat.getColor(applicationContext, R.color.black)
            )

            // Set the background color of the fragment container according to the app theme.
            binding.navHostFragment.setBackgroundColor(
                ContextCompat.getColor(applicationContext, R.color.light_gray)
            )
        }
    }
}