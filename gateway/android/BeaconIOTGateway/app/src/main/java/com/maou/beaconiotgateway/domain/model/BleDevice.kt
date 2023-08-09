package com.maou.beaconiotgateway.domain.model

data class BleDevice(
    val deviceName: String,
    val deviceAddress: String,
    val rssi: Int,
    val timestamp: Long
)
