package com.anastasiaiu.dttrealestate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentOverviewBinding
import com.anastasiaiu.dttrealestate.model.remote.HouseApiStatus
import com.anastasiaiu.dttrealestate.view.DttRealEstateActivity
import com.anastasiaiu.dttrealestate.view.adapter.HouseListAdapter
import com.anastasiaiu.dttrealestate.view.utilities.MarginItemDecoration
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModel

/**
TODO: Add behavior to the search. When active hide hint and change icon. Fix focus on the search and invoking keyboard. Lose focus after exit the search.
textField.setEndIconOnClickListener {
// Respond to end icon presses
}

textField.addOnEditTextAttachedListener {
// If any specific changes should be done when the edit text is attached (and
// thus when the trailing icon is added to it), set an
// OnEditTextAttachedListener.

// Example: The clear text icon's visibility behavior depends on whether the
// EditText has input present. Therefore, an OnEditTextAttachedListener is set
// so things like editText.getText() can be called.
}
 */

/**
 * A fragment representing a list of Items.
 */
class OverviewFragment : Fragment() {

    private var _viewModel: HouseViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    lateinit var houseListAdapter: HouseListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        houseListAdapter = HouseListAdapter(onclick(), onclick())

        binding.recyclerView.apply {
            adapter = houseListAdapter

            addItemDecoration(MarginItemDecoration(bottomMargin = resources.getDimension(R.dimen.container_margin).toInt()))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = (activity as DttRealEstateActivity).houseViewModel

        viewModel.status.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                HouseApiStatus.LOADING -> binding.overviewFragmentToolbar.title = "Loading"
                HouseApiStatus.ERROR -> binding.overviewFragmentToolbar.title = "Error"
                HouseApiStatus.SUCCESS -> houseListAdapter.submitList(viewModel.housesList.value)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onclick() {}
}