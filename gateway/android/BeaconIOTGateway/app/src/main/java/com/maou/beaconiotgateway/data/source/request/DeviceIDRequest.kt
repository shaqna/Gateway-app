package com.maou.beaconiotgateway.data.source.request

data class DeviceIDRequest(
    val item: DeviceIDItem
)

data class DeviceIDItem(
    val deviceID: String
)