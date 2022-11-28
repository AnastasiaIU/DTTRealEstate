package com.anastasiaiu.dttrealestate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentFavouritesBinding
import com.anastasiaiu.dttrealestate.view.adapter.FavouritesListAdapter
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.utilities.MarginItemDecoration
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FavouritesFragment : Fragment() {

    private var _viewModel: HouseViewModel? = null
    private val viewModel get() = _viewModel!!

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProvider(requireActivity())[HouseViewModel::class.java]
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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
                "https://picsum.photos/id/164/500",
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