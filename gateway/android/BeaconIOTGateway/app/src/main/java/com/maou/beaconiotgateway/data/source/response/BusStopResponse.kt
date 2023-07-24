package com.maou.beaconiotgateway.data.source.response

import com.maou.beaconiotgateway.domain.model.BusStop

data class BusStopResponse(
    val code: Int,
    val message: String,
    val data: List<BusStop>
)
