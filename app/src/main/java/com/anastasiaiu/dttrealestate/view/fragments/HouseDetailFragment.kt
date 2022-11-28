package com.anastasiaiu.dttrealestate.view.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentHouseDetailBinding
import com.anastasiaiu.dttrealestate.viewmodel.HouseViewModel
import com.google.android.material.color.MaterialColors
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HouseDetailFragment : Fragment() {

    private var _viewModel: HouseViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentHouseDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHouseDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProvider(requireActivity())[HouseViewModel::class.java]

        val backgroundColor = MaterialColors.getColor(binding.root, android.R.attr.colorBackground)

        customRoundedCornersView(binding.roundedContainer, backgroundColor)

        val localCurrency =
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
                Currency.getInstance(Locale.getDefault()).symbol ?: ""
            } else {
                ""
            }

        val price = localCurrency + "%,d".format(68000)

        binding.houseDetailPrice.text = price
        binding.houseDetailBedrooms.text = "3"
        binding.houseDetailBathrooms.text = "2"
        binding.houseDetailSize.text = "400"
        binding.houseDetailDistance.text = "65"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun customRoundedCornersView(view: View, backgroundColor: Int) {

        val cornerRadius = resources.getDimension(
            R.dimen.house_detail_rounded_container_corner_radius
        )

        val shape = GradientDrawable()

        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadii =
            floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f, 0f, 0f)
        shape.setColor(backgroundColor)

        view.background = shape
    }
}