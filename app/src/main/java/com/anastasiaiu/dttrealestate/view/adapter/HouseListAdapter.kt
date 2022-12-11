package com.anastasiaiu.dttrealestate.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.HouseCardViewBinding
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.utilities.FormatValue

/**
 * [HouseListAdapter] for displaying the [House].
 */
class HouseListAdapter(
    private val distanceToLocation: (Double, Double) -> Double?,
    private val bookmarkOnClickListener: (House, Int) -> Unit,
    private val houseCardOnClickListener: (House) -> Unit
) : ListAdapter<House, HouseListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: HouseCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds [house] values to the [ViewHolder] and sets click listeners.
         */
        fun bind(
            house: House,
            distanceToLocation: (Double, Double) -> Double?,
            bookmarkOnClickListener: (House, Int) -> Unit,
            houseCardOnClickListener: (House) -> Unit
        ) {

            // Distance to a house from the device location.
            val distance = distanceToLocation(house.latitude.toDouble(), house.longitude.toDouble())

            // Get the bookmark icon depending on the state.
            val isHouseBookmarked = house.isBookmarked
            val bookmarkIcon = if (isHouseBookmarked) {
                R.drawable.ic_baseline_bookmark_24
            } else {
                R.drawable.ic_outline_bookmark_border_24
            }

            binding.apply {
                houseCardPicture.load(house.imageUrl)
                houseCardPrice.text = FormatValue.formatPrice(house.price)
                houseCardLocation.text = FormatValue.formatAddress(house)
                houseCardBedrooms.text = house.bedrooms.toString()
                houseCardBathrooms.text = house.bathrooms.toString()
                houseCardSize.text = house.size.toString()
                houseCardIconBookmark.setImageResource(bookmarkIcon)

                // Hide the location icon if the distance is null,
                // otherwise set the distance value.
                if (distance == null) {
                    houseCardIconDistance.visibility = View.GONE
                } else {
                    houseCardDistance.text = FormatValue.formatDistance(distance)
                }

                // Set click listeners for the card and the bookmark.
                root.setOnClickListener { houseCardOnClickListener(house) }
                houseCardIconBookmark.setOnClickListener {
                    house.isBookmarked = !isHouseBookmarked
                    bookmarkOnClickListener(house, bindingAdapterPosition)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<House>() {

        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HouseCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(
            getItem(position),
            distanceToLocation,
            bookmarkOnClickListener,
            houseCardOnClickListener
        )
    }
}