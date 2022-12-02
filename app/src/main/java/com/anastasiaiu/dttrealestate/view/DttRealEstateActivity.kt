package com.anastasiaiu.dttrealestate.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.ActivityRealEstateAppBinding

/**
 * The main activity of the application.
 */
class DttRealEstateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the splash screen theme.
        setTheme(R.style.Theme_App_SplashScreen)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )

        super.onCreate(savedInstanceState)

        // Set the main theme for the application.
        setTheme(R.style.Theme_DTTRealEstate)

        // Request the location permission if it not granted yet.
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Inflate layout.
        val binding = ActivityRealEstateAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set navigation bar.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom navigation bar at the house details fragment
        // for the correct navigation in the application.
        navController.addOnDestinationChangedListener { _, navDestination, _ ->

            if (navDestination.id == R.id.house_detail_fragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        // Fix status bar background color after the splash screen for API 28 and lower.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {

            // Set the background color of the activity to black.
            // Fixes the issue with the white icons at the status bar.
            binding.root.setBackgroundColor(
                ContextCompat.getColor(applicationContext, R.color.black)
            )

            // Set the background color of the container for fragments according to the app theme.
            binding.navHostFragment.setBackgroundColor(
                ContextCompat.getColor(applicationContext, R.color.light_gray)
            )
        }
    }
}