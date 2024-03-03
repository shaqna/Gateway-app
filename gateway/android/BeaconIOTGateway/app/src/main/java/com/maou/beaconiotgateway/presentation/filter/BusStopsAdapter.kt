package com.maou.beaconiotgateway.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maou.beaconiotgateway.databinding.DropdownItemBinding
import com.maou.beaconiotgateway.databinding.ItemBusStopsBinding
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop

class BusStopsAdapter : RecyclerView.Adapter<BusStopsAdapter.ViewHolder>() {
    private val busStops = arrayListOf<BusStop>()
    private var isNewRadioButtonChecked = false
    private var lastCheckedPosition = 0

    private var onItemSelectedListener: OnItemSelectedCallback? = null

    fun setOnItemCallback(onItemSelected: (selectedBusStop: BusStop) -> Unit) {
        onItemSelectedListener = object : OnItemSelectedCallback {
            override fun onItemSelected(busStop: BusStop) {
                onItemSelected(busStop)
            }
        }
    }

     fun setList(list: List<BusStop>) {
        busStops.clear()
        busStops.addAll(list)
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopsAdapter.ViewHolder {
        val itemBinding = ItemBusStopsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BusStopsAdapter.ViewHolder, position: Int) {
        holder.bind(busStops[position])
    }

    override fun getItemCount(): Int = busStops.size


    inner class ViewHolder(private val binding: ItemBusStopsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(busStop: BusStop) {
            with(binding) {
                tvBusStopName.text = busStop.name
                if(isNewRadioButtonChecked) {
                    radioButton.isChecked = busStop.active
                }

                radioButton.setOnClickListener {
                    handleRadioButtonChecks(adapterPosition)
                    onItemSelectedListener?.onItemSelected(busStop)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun handleRadioButtonChecks(adapterPosition: Int) {
        isNewRadioButtonChecked = true
        busStops[lastCheckedPosition].active = false
        busStops[adapterPosition].active = true
        lastCheckedPosition = adapterPosition
        notifyDataSetChanged()
    }
}

interface OnItemSelectedCallback {
    fun onItemSelected(busStop: BusStop)
}