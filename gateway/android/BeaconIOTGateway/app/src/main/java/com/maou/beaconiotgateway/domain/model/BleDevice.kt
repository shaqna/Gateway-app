package com.maou.beaconiotgateway.domain.model

data class BleDevice(
    val deviceAddress: String,
    val rssi: Int,
    val timestamp: Long,
    val txPower: Int,
    val proximityUUID: String?,
    val distance: Double,
)
