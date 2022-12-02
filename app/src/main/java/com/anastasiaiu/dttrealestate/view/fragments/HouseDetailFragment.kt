package com.anastasiaiu.dttrealestate.view.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentHouseDetailBinding
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.utilities.FormatValue
import com.google.android.gms.maps.CameraUpdateFactory
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

    // Google map callback to be displayed when it is ready.
    private val mapCallback = OnMapReadyCallback { googleMap ->

        val location = LatLng(
            viewModel.currentHouse.latitude.toDouble(),
            viewModel.currentHouse.longitude.toDouble()
        )

        val marker = MarkerOptions().position(location).title(getString(R.string.map_marker_title))

        googleMap.apply {
            addMarker(marker)
            animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHouseDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the custom rounded container.
        val backgroundColor = MaterialColors.getColor(binding.root, android.R.attr.colorBackground)
        customRoundedCornersView(binding.roundedContainer, backgroundColor)

        // Current house to be displayed.
        val house = viewModel.currentHouse

        // Distance to a house from the device location.
        val distance = getDistanceFromDevice(house.latitude.toDouble(), house.longitude.toDouble())

        setBookmarkIcon(house)

        binding.apply {
            houseDetailPicture.load(house.imageUrl)
            houseDetailPrice.text = FormatValue.formatPrice(house.price)
            houseDetailBedrooms.text = house.bedrooms.toString()
            houseDetailBathrooms.text = house.bathrooms.toString()
            houseDetailSize.text = house.size.toString()
            houseDetailDistance.text = FormatValue.formatDistance(distance)
            houseDetailTextDescription.text = house.description

            // Set onClickListeners for the card and the bookmark.
            houseDetailIconBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            houseDetailIconBookmark.setOnClickListener { onBookmarkClicked(house) }
        }

        // Set the Google map.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(mapCallback)
    }

    /**
     * Creates custom container with rounded corners.
     */
    private fun customRoundedCornersView(view: View, backgroundColor: Int) {

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

        view.background = gradientDrawable
    }

    /**
     * Calls the function from the viewModel to insert a house
     * into the database with a changed bookmark state.
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
}