package com.anastasiaiu.dttrealestate.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.ActivityRealEstateAppBinding
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.REQUEST_CODE

/**
 * The main activity of the application.
 */
class DttRealEstateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
                    REQUEST_CODE
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

        // Hide the status bar with white icons for API levels 22 and lower.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            hideStatusBar()
        }
    }

    /**
     * Hides status bar.
     */
    private fun hideStatusBar() {

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // Configure the behavior of the hidden system bars.
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Hide the status bar.
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    }
}