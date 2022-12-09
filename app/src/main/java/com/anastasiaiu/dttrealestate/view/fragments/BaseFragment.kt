package com.anastasiaiu.dttrealestate.view.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anastasiaiu.dttrealestate.DttRealEstateApplication
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModel
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

/**
 * The base fragment contains base logic for [OverviewFragment],
 * [FavouritesFragment] and [HouseDetailFragment].
 */
abstract class BaseFragment : Fragment() {

    val viewModel: HouseViewModel by activityViewModels {
        HouseViewModelFactory(requireActivity().application as DttRealEstateApplication)
    }

    /**
     * Returns the distance in kilometers between the device and the provided coordinates.
     */
    fun getDistanceFromDevice(latitude: Double, longitude: Double): Double? {

        if (viewModel.deviceLatitude == null || viewModel.deviceLongitude == null) return null

        val startLatLng = LatLng(viewModel.deviceLatitude!!, viewModel.deviceLongitude!!)
        val endLatLng = LatLng(latitude, longitude)

        return SphericalUtil.computeDistanceBetween(startLatLng, endLatLng) / 1000
    }

    /**
     * Sets the current house at the viewModel.
     */
    open fun houseCardOnClickListener(house: House) {
        viewModel.onHouseCardClicked(house)
    }
}