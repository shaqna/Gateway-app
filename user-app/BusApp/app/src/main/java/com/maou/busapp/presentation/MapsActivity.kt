package com.maou.busapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.maou.busapp.R
import com.maou.busapp.databinding.ActivityMapsBinding
import com.maou.busapp.helper.UiHelper
import kotlin.math.roundToInt

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding
    private val sydney = LatLng(5.570319770612586, 95.36968557931598)

    private lateinit var bottomSheet: ConstraintLayout

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var uiHelper: UiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheet = binding.currentLocationBottomSheetLayout.deviceBottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        uiHelper = UiHelper(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.currentLocationBottomSheetLayout.btnFindBus.setOnClickListener {

        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        mMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))

        bottomSheetBehavior.apply {
            peekHeight = 200
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        adjustMapPaddingToBottomSheet(binding.currentLocationBottomSheetLayout.deviceBottomSheet.height)

    }


    private val bottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    Log.d("BottomBehaviorState", "EXPANDED")
                    adjustMapPaddingToBottomSheet(binding.currentLocationBottomSheetLayout.deviceBottomSheet.height)
                }

                BottomSheetBehavior.STATE_COLLAPSED -> {
                    Log.d("BottomBehaviorState", "COLLAPSED")
                    adjustMapPaddingToBottomSheet(bottomSheetBehavior.peekHeight)
                }

                else -> {

                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_DRAGGING -> {
                    Log.d("BottomBehaviorState", "DRAGGING")
                    adjustMapPaddingByOffset(slideOffset)
                }

                BottomSheetBehavior.STATE_SETTLING -> {
                    Log.d("BottomBehaviorState", "SETTLING")
                    adjustMapPaddingByOffset(slideOffset)
                }

                else -> {

                }

            }
        }

    }

    private fun adjustMapPaddingByOffset(slideOffset: Float) {
        val bottomSheetHeight = binding.currentLocationBottomSheetLayout.deviceBottomSheet.height
        val offset = bottomSheetHeight * slideOffset
        val appBarHeight = binding.appbar.appbarLayout.height
        mMap?.setPadding(0, appBarHeight, 0, (offset).roundToInt())
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))
    }

    private fun adjustMapPaddingToBottomSheet(bottomPadding: Int) {
        val appBarHeight = binding.appbar.appbarLayout.height
        mMap?.setPadding(0, appBarHeight, 0, bottomPadding)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))
    }


}