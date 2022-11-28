package com.anastasiaiu.dttrealestate.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentHouseCardBinding
import com.anastasiaiu.dttrealestate.model.House
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class OverviewListAdapter(private val values: List<House>) :
    RecyclerView.Adapter<OverviewListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentHouseCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val house = values[position]

        val localCurrency =
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
                Currency.getInstance(Locale.getDefault()).symbol ?: ""
            } else {
                ""
            }

        val price = localCurrency + "%,d".format(house.price)
        val location = house.zip.replace(" ","") + " " + house.city
        val distance = (house.longitude + house.latitude).toString()

        holder.housePictureImageView.load(house.imageUrl)
        holder.housePriceTextView.text = price
        holder.houseLocationTextView.text = location
        holder.houseBedroomsTextView.text = house.bedrooms.toString()
        holder.houseBathroomsTextView.text = house.bathrooms.toString()
        holder.houseSizeTextView.text = house.size.toString()
        holder.houseDistanceTextView.text = distance
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHouseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_overview_fragment_to_house_detail_fragment)
            }
        }

        val housePictureImageView: ImageView = binding.houseCardPicture
        val housePriceTextView: TextView = binding.houseCardPrice
        val houseLocationTextView: TextView = binding.houseCardLocation
        val houseBedroomsTextView: TextView = binding.houseCardBedrooms
        val houseBathroomsTextView: TextView = binding.houseCardBathrooms
        val houseSizeTextView: TextView = binding.houseCardSize
        val houseDistanceTextView: TextView = binding.houseCardDistance
    }
}