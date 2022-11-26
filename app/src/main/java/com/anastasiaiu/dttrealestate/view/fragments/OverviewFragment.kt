package com.anastasiaiu.dttrealestate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentOverviewBinding
import com.anastasiaiu.dttrealestate.view.House
import com.anastasiaiu.dttrealestate.view.utilities.MarginItemDecoration
import com.anastasiaiu.dttrealestate.view.adapters.OverviewListAdapter

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

    //@Inject
    //lateinit var houseList: List<House>

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            adapter = OverviewListAdapter(houseList)

            addItemDecoration(MarginItemDecoration(bottomMargin = resources.getDimension(R.dimen.container_margin).toInt()))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val houseList = listOf(
            House(
                1,
                "1",
                45000,
                1,
                1,
                1,
                "1",
                "1000 VV",
                "Den Haag",
                1,
                1,
                "1"
            ),
            House(
                2,
                "2",
                2,
                2,
                2,
                2,
                "2",
                "2",
                "2",
                2,
                2,
                "2"
            ),
            House(
                3,
                "3",
                3,
                3,
                3,
                3,
                "3",
                "3",
                "3",
                3,
                3,
                "3"
            ),
            House(
                4,
                "4",
                4,
                4,
                4,
                4,
                "4",
                "4",
                "4",
                4,
                4,
                "4"
            ),
            House(
                5,
                "5",
                5,
                5,
                5,
                5,
                "5",
                "5",
                "5",
                5,
                5,
                "5"
            ),
            House(
                6,
                "6",
                6,
                6,
                6,
                6,
                "6",
                "6",
                "6",
                6,
                6,
                "6"
            ),
            House(
                7,
                "7",
                7,
                7,
                7,
                7,
                "7",
                "7",
                "7",
                7,
                7,
                "7"
            ),
            House(
                8,
                "8",
                8,
                8,
                8,
                8,
                "8",
                "8",
                "8",
                8,
                8,
                "8"
            ),
            House(
                9,
                "9",
                9,
                9,
                9,
                9,
                "9",
                "9",
                "9",
                9,
                9,
                "9"
            ),
            House(
                10,
                "10",
                10,
                10,
                10,
                10,
                "10",
                "10",
                "10",
                10,
                10,
                "10"
            )
        )
    }
}