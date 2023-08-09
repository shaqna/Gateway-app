package com.maou.beaconiotgateway.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStop(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val active: Boolean
): Parcelable
