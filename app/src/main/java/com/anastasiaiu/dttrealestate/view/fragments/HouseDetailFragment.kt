package com.anastasiaiu.dttrealestate.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.*
import coil.load
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentHouseDetailBinding
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.utilities.FormatValue
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.GOOGLE_MAPS_PACKAGE
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.GOOGLE_MAP_ZOOM
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.getGoogleNavigationUri
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.color.MaterialColors


/**
 * [HouseDetailFragment] displays the details of a house.
 */
class HouseDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentHouseDetailBinding
    private lateinit var window: Window
    private lateinit var decorView: View
    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    // Google map callback to be displayed when the map is ready.
    private val mapCallback = OnMapReadyCallback { setOnMapReadyCallback(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHouseDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        window = requireActivity().window
        decorView = window.decorView
        windowInsetsController = WindowCompat.getInsetsController(window, decorView)

        // Set the correct bottom padding for API levels 30 and higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            view.doOnAttach {
                it.updatePadding(
                    bottom = decorView.rootWindowInsets.getInsets(
                        WindowInsetsCompat.Type.mandatorySystemGestures()
                    ).bottom
                )
            }
        }

        val backgroundColor = MaterialColors.getColor(binding.root, android.R.attr.colorBackground)

        // Set the custom rounded container.
        binding.roundedContainer.setUpperRoundedCorners(backgroundColor)

        bindHouse(viewModel.currentHouse)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(mapCallback)
    }

    override fun onResume() {
        super.onResume()

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)

        // Set the status bar icons to be white, so they match the screen icons.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            windowInsetsController.isAppearanceLightStatusBars = false
        }
    }

    override fun onStop() {
        super.onStop()

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)

        // Set the status bar icons' color to match the current mode.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            windowInsetsController.isAppearanceLightStatusBars =
                resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_YES
        }
    }

    /**
     * Sets the [View] background to be the [backgroundColor] with rounded upper corners.
     */
    private fun View.setUpperRoundedCorners(backgroundColor: Int) {

        val cornerRadius = resources.getDimension(
            R.dimen.house_detail_rounded_container_corner_radius
        )

        val gradientDrawable = GradientDrawable()

        gradientDrawable.apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(
                cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f, 0f, 0f
            )
            setColor(backgroundColor)
        }

        this.background = gradientDrawable
    }

    /**
     * Calls the function from the view model to insert a house
     * into the database with a changed bookmark state. Sets the bookmark icon.
     */
    private fun onBookmarkClicked(house: House) {

        house.isBookmarked = !house.isBookmarked

        viewModel.addBookmarkedHouseToDatabase(house)

        setBookmarkIcon(house)
    }

    /**
     * Sets the bookmark icon depending on the state.
     */
    private fun setBookmarkIcon(house: House) {

        // Get the bookmark icon depending on the state.
        val isHouseBookmarked = house.isBookmarked
        val bookmarkIcon = if (isHouseBookmarked) {
            R.drawable.ic_baseline_bookmark_24
        } else {
            R.drawable.ic_outline_bookmark_border_24
        }

        // Set the bookmark icon.
        binding.houseDetailIconBookmark.setImageResource(bookmarkIcon)
    }

    /**
     * Binds [house] values to the view.
     */
    private fun bindHouse(house: House) {

        // Distance to a house from the device location.
        val distance = getDistanceFromDevice(house.latitude.toDouble(), house.longitude.toDouble())

        setBookmarkIcon(house)

        binding.apply {
            houseDetailPicture.load(house.imageUrl)
            houseDetailPrice.text = FormatValue.formatPrice(house.price)
            houseDetailBedrooms.text = house.bedrooms.toString()
            houseDetailBathrooms.text = house.bathrooms.toString()
            houseDetailSize.text = house.size.toString()
            houseDetailTextDescription.text = house.description

            // Hide the location icon if the distance is null,
            // otherwise set the distance value.
            if (distance == null) {
                houseDetailIconDistance.visibility = View.GONE
            } else {
                houseDetailDistance.text = FormatValue.formatDistance(distance)
            }

            // Set click listeners for the back and the bookmark icons.
            houseDetailIconBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            houseDetailIconBookmark.setOnClickListener { onBookmarkClicked(house) }
        }
    }

    /**
     * Starts Google map navigation intent.
     */
    private fun startGoogleMapNavigationIntent(location: LatLng) {

        val gmmIntentUri = Uri.parse(
            getGoogleNavigationUri(location.latitude, location.longitude)
        )
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        mapIntent.apply {
            setPackage(GOOGLE_MAPS_PACKAGE)
            resolveActivity(requireContext().packageManager)?.let { startActivity(this) }
        }
    }

    /**
     * Sets content for the [googleMap].
     */
    @SuppressLint("PotentialBehaviorOverride")
    private fun setOnMapReadyCallback(googleMap: GoogleMap) {

        val location = LatLng(
            viewModel.currentHouse.latitude.toDouble(),
            viewModel.currentHouse.longitude.toDouble()
        )

        val marker = MarkerOptions().position(location)

        googleMap.apply {

            addMarker(marker)

            // Zoom camera to the marker.
            animateCamera(CameraUpdateFactory.newLatLngZoom(location, GOOGLE_MAP_ZOOM))

            // Prevent parent views from intercepting the map touch events.
            setOnCameraMoveStartedListener {
                if (it == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    binding.map.requestDisallowInterceptTouchEvent(true)
                }
            }
            setOnCameraMoveCanceledListener {
                binding.map.requestDisallowInterceptTouchEvent(false)
            }

            // Set on marker click listener to start Google maps navigation intent.
            setOnMarkerClickListener {
                startGoogleMapNavigationIntent(location)
                true
            }
        }
    }
}