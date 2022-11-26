package com.anastasiaiu.dttrealestate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentFavouritesBinding
import com.anastasiaiu.dttrealestate.view.adapters.FavouritesListAdapter
import com.anastasiaiu.dttrealestate.view.House
import com.anastasiaiu.dttrealestate.view.utilities.MarginItemDecoration

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            adapter = FavouritesListAdapter(houseList)

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