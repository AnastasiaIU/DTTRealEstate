package com.anastasiaiu.dttrealestate.view.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentOverviewBinding
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.adapter.HouseListAdapter
import com.anastasiaiu.dttrealestate.view.utilities.HouseApiStatus
import com.anastasiaiu.dttrealestate.view.utilities.MarginItemDecoration
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.SEARCH_FIELD_DELAY
import com.anastasiaiu.dttrealestate.view.utilities.ViewConstants.SOFT_INPUT_NO_FLAGS
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*

/**
 * [OverviewFragment] displays a list of the available houses.
 */
class OverviewFragment : BaseFragment() {

    private lateinit var binding: FragmentOverviewBinding
    private lateinit var houseListAdapter: HouseListAdapter
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater, container, false)

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
        observeRespondStatus()
        observeSearchQuery()

        // Set behavior for the search field.
        setSearchFieldBehavior()

        // Set text change listener at the search field.
        addSearchQuery()
    }

    override fun onResume() {
        super.onResume()

        // Set focus on the search field if it is not empty.
        binding.searchEditText.text?.let {
            if (it.isNotBlank()) binding.searchEditText.requestFocus()
        }
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
            R.id.action_overview_fragment_to_house_detail_fragment
        )
    }

    /**
     * Sets observer to the status of the respond from the server.
     */
    private fun observeRespondStatus() {

        viewModel.status.observe(viewLifecycleOwner) { response ->
            when (response) {
                HouseApiStatus.LOADING -> {
                    viewModel.emptyState.postValue(true)
                    binding.emptyStateText.text = getString(R.string.empty_state_loading)
                }
                HouseApiStatus.SUCCESS -> {
                    viewModel.apply {
                        emptyState.postValue(false)
                        housesList.observe(viewLifecycleOwner) { list ->
                            houseListAdapter.submitList(list)
                        }
                    }
                    binding.emptyStateText.text = getString(R.string.empty_state_search)
                }
                else -> {
                    viewModel.emptyState.postValue(true)
                    binding.emptyStateText.text = getString(R.string.empty_state_error)
                }
            }
        }
    }

    /**
     * Sets observer to the search query to show results of the search or the full list of houses.
     */
    private fun observeSearchQuery() {

        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->

            if (query.isNotBlank()) {

                viewModel.apply {
                    housesList.removeObservers(viewLifecycleOwner)
                    searchHousesList.observe(viewLifecycleOwner) { list ->
                        houseListAdapter.submitList(list)
                        emptyState.postValue(list.isEmpty())
                    }
                }

            } else {

                viewModel.apply {
                    searchHousesList.removeObservers(viewLifecycleOwner)
                    housesList.observe(viewLifecycleOwner) { list ->
                        houseListAdapter.submitList(list)
                    }
                    emptyState.postValue(false)
                }
            }
        }
    }

    /**
     * Sets the trailing icon and it's behavior at the search field.
     */
    private fun setSearchFieldBehavior() {

        val inputMethodManager = requireActivity()
            .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.searchEditText.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus || (v as TextInputEditText).text!!.isNotEmpty()) {

                binding.searchLayout.apply {

                    // Set the close icon.
                    setEndIconDrawable(R.drawable.ic_close)

                    // Set on click listener on the icon.
                    setEndIconOnClickListener {

                        (v as TextInputEditText).apply {
                            text!!.clear()
                            clearFocus()
                        }

                        // Hide keyboard.
                        inputMethodManager.hideSoftInputFromWindow(
                            binding.root.windowToken, SOFT_INPUT_NO_FLAGS
                        )
                    }
                }

            } else {

                binding.searchLayout.apply {

                    // Set the search icon.
                    setEndIconDrawable(R.drawable.ic_search)

                    // Set on click listener on the icon.
                    setEndIconOnClickListener {

                        v.requestFocus()

                        // Show keyboard.
                        inputMethodManager.showSoftInput(v, SOFT_INPUT_NO_FLAGS)
                    }
                }
            }
        }
    }

    /**
     * Add search query and results of it with a delay
     * to the view model when text is changed at the search field.
     */
    private fun addSearchQuery() {

        binding.searchEditText.addTextChangedListener { editable ->

            val text = editable.toString().trim()

            job?.cancel()

            job = MainScope().launch(Dispatchers.IO) {

                delay(SEARCH_FIELD_DELAY)

                viewModel.apply {
                    searchQuery.postValue(text)
                    search(text)
                }
            }
        }
    }
}