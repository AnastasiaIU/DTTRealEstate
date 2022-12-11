package com.anastasiaiu.dttrealestate.view.fragments

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anastasiaiu.dttrealestate.DttRealEstateApplication
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.M_TO_KM
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModel
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

/**
 * The [BaseFragment] contains base logic for [OverviewFragment],
 * [FavouritesFragment] and [HouseDetailFragment].
 */
abstract class BaseFragment : Fragment() {

    /**
     * An instance of the shared [HouseViewModel].
     */
    val viewModel: HouseViewModel by activityViewModels {
        HouseViewModelFactory(requireActivity().application as DttRealEstateApplication)
    }

    /**
     * Returns the distance in kilometers between the device and the provided coordinates.
     *
     * @return kilometers as [Double] or null if the device location is unavailable.
     */
    fun getDistanceFromDevice(latitude: Double, longitude: Double): Double? {

        if (viewModel.deviceLatitude == null || viewModel.deviceLongitude == null) return null

        val startLatLng = LatLng(viewModel.deviceLatitude!!, viewModel.deviceLongitude!!)
        val endLatLng = LatLng(latitude, longitude)

        return SphericalUtil.computeDistanceBetween(startLatLng, endLatLng) / M_TO_KM
    }

    /**
     * Sets the [house] to be the current house at the view model.
     */
    open fun setCurrentHouseAtViewModel(house: House) {
        viewModel.applyCurrentHouse(house)
    }

    /**
     * Sets observer to the status of the empty state to track
     * if the [emptyStateContainer] is needed to be shown.
     */
    fun observeEmptyStateStatus(emptyStateContainer: ConstraintLayout) {

        viewModel.emptyState.observe(viewLifecycleOwner) { statusIsTrue ->
            if (statusIsTrue) {
                emptyStateContainer.visibility = View.VISIBLE
            } else {
                emptyStateContainer.visibility = View.GONE
            }
        }
    }
}