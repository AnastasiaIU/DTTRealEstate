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
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * [OverviewFragment] displays a list of the available houses.
 */
class OverviewFragment : BaseFragment() {

    private lateinit var binding: FragmentOverviewBinding
    private lateinit var houseListAdapter: HouseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        houseListAdapter = HouseListAdapter(
            ::getDistanceFromDevice,
            ::onBookmarkClicked,
            ::houseCardOnClickListener
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

        // Observe status of the empty state to track if it is needed to be shown.
        viewModel.emptyState.observe(viewLifecycleOwner) { statusIsTrue ->
            if (statusIsTrue) {
                binding.emptyStateContainer.visibility = View.VISIBLE
            } else {
                binding.emptyStateContainer.visibility = View.GONE
            }
        }

        // Observe status of the respond from the server.
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

        // Observe search query to show results of the search or the full list of houses.
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


        // Add the clear text icon's visibility and it's behavior for the search.
        binding.searchEditText.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus || (v as TextInputEditText).text!!.isNotEmpty()) {

                binding.searchLayout.apply {
                    setEndIconDrawable(R.drawable.ic_close)
                    setEndIconOnClickListener {
                        (v as TextInputEditText).apply {
                            text!!.clear()
                            clearFocus()
                        }

                        // Hide keyboard.
                        val inputMethodManager = requireActivity()
                            .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }

            } else {
                binding.searchLayout.setEndIconDrawable(R.drawable.ic_search)
            }
        }

        // Add search query and results of it with a delay to the viewModel when text is changed.
        var job: Job? = null

        binding.searchEditText.addTextChangedListener { editable ->

            job?.cancel()

            job = MainScope().launch {

                delay(500L)

                viewModel.apply {
                    searchQuery.postValue(editable.toString())
                    searchHousesList.postValue(viewModel.search(editable.toString()))
                }
            }
        }
    }

    /**
     * Calls the function from the viewModel to insert a house
     * into the database with a changed bookmark state.
     */
    private fun onBookmarkClicked(house: House, position: Int) {

        viewModel.addBookmarkedHouseToDatabase(house)

        binding.recyclerView.adapter!!.notifyItemChanged(position)
    }

    /**
     * Sets the current house at the viewModel. Navigates to the house detail view.
     */
    override fun houseCardOnClickListener(house: House) {
        super.houseCardOnClickListener(house)

        binding.root.findNavController().navigate(
            R.id.action_overview_fragment_to_house_detail_fragment
        )
    }
}