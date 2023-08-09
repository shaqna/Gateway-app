package com.maou.beaconiotgateway.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.maou.beaconiotgateway.R
import com.maou.beaconiotgateway.databinding.DropdownItemBinding
import com.maou.beaconiotgateway.domain.model.Bus

class BusArrayAdapter(private val context: Context, list: List<Bus>) :
    ArrayAdapter<Bus>(context, 0, list)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bus = getItem(position)
        val view = DropdownItemBinding.inflate(LayoutInflater.from(context),parent, false)
        view.name.text = bus?.name
        return view.root
    }

}