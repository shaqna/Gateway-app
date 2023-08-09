package com.maou.beaconiotgateway.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.maou.beaconiotgateway.databinding.DropdownItemBinding
import com.maou.beaconiotgateway.domain.model.BusStop

class BusStopArrayAdapter(private val context: Context, private val list: List<BusStop>) :
    ArrayAdapter<BusStop>(context, 0, list)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val busStop = getItem(position)
        val view = DropdownItemBinding.inflate(LayoutInflater.from(context), parent, false)
        view.name.text = busStop?.name
        return view.root
    }
}