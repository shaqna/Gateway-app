package com.maou.beaconiotgateway.data.source.response

import com.maou.beaconiotgateway.domain.model.Bus

data class BusResponse(
    val code: Int,
    val message: String,
    val data: List<Bus>
)
