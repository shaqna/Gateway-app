package com.maou.beaconiotgateway.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bus(
    val id: String,
    val name: String,
    val address: String
): Parcelable
