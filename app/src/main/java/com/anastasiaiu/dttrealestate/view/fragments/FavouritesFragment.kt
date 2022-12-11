package com.anastasiaiu.dttrealestate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentFavouritesBinding
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.adapter.HouseListAdapter
import com.anastasiaiu.dttrealestate.view.utilities.MarginItemDecoration

/**
 * [FavouritesFragment] displays a list of the bookmarked houses.
 */
class FavouritesFragment : BaseFragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var houseListAdapter: HouseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        houseListAdapter = HouseListAdapter(
            ::getDistanceFromDevice,
            ::onBookmarkClicked,
            ::setCurrentHouseAtViewModel
        )

        binding.recyclerView.apply {

            adapter = houseListAdapter

            addItemDecoration(
                MarginItemDecoration(
                    bottomMargin = resources.getDimension(R.dimen.container_margin).toInt()
                )
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set observers.
        observeEmptyStateStatus(binding.emptyStateContainer)
        observeBookmarkedHousesList()
    }

    /**
     * Calls the function from the view model to insert a house
     * into the database with a changed bookmark state.
     */
    private fun onBookmarkClicked(house: House, position: Int) {

        viewModel.addBookmarkedHouseToDatabase(house)

        binding.recyclerView.adapter?.notifyItemChanged(position)
    }

    /**
     * Sets the [house] to be the current house at the view model.
     * Navigates to the house detail view.
     */
    override fun setCurrentHouseAtViewModel(house: House) {
        super.setCurrentHouseAtViewModel(house)

        binding.root.findNavController().navigate(
            R.id.action_favourites_fragment_to_house_detail_fragment
        )
    }

    /**
     * Sets observer to the changes at bookmarked houses list to display them.
     */
    private fun observeBookmarkedHousesList() {

        viewModel.bookmarkedHousesList.observe(viewLifecycleOwner) { list ->
            houseListAdapter.submitList(list)
            viewModel.emptyState.postValue(list.isEmpty())
        }
    }
}