package com.anastasiaiu.dttrealestate.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anastasiaiu.dttrealestate.databinding.FragmentHouseCardBinding
import com.anastasiaiu.dttrealestate.model.House
import com.anastasiaiu.dttrealestate.view.adapter.HouseListAdapter.ViewHolder
import java.util.*

/**
 * [HouseListAdapter] that can display a [House].
 * TODO: Implement logic for the distance at the [ViewHolder].
 */
class HouseListAdapter(
    private val cardOnClickListener: Unit,
    private val bookmarkOnClickListener: Unit
) : ListAdapter<House, HouseListAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: FragmentHouseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            house: House,
            cardOnClickListener: Unit,
            bookmarkOnClickListener: Unit
        ) {

            val localCurrency = Currency.getInstance(Locale.getDefault()).symbol

            val price = localCurrency + "%,d".format(house.price)
            val location = house.zip.replace(" ", "") + " " + house.city
            val distance = (house.longitude + house.latitude).toString()

            binding.apply {
                houseCardPicture.load(house.imageUrl)
                houseCardPrice.text = price
                houseCardLocation.text = location
                houseCardBedrooms.text = house.bedrooms.toString()
                houseCardBathrooms.text = house.bathrooms.toString()
                houseCardSize.text = house.size.toString()
                houseCardDistance.text = distance
                houseCardIconBookmark.setOnClickListener { bookmarkOnClickListener }
                root.setOnClickListener { cardOnClickListener }
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
            FragmentHouseCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val house = getItem(position)

        holder.bind(house, cardOnClickListener, bookmarkOnClickListener)
    }
}