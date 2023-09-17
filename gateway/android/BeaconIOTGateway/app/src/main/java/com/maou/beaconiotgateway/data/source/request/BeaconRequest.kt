package com.maou.beaconiotgateway.data.source.request

import com.squareup.moshi.Json

data class BeaconRequest(
    @field:Json(name = "operation")
    val operation: String = "post-beacon",

    @field:Json(name = "payload")
    val payload: Item
)

data class Item(
    @field:Json(name = "Item")
    val item: Payload
)

data class Payload(
    @field:Json(name = "timestamp")
    val timestamp: Long,

    @field:Json(name = "deviceID")
    val deviceID: String,

    @field:Json(name = "bleAddress")
    val bleAddress: String,

    @field:Json(name = "distance")
    val distance: Double,

    @field:Json(name = "isRead")
    val isRead: Int = 0,

    @field:Json(name = "proximityUUID")
    val proximityUUID: String,

    @field:Json(name = "rssi")
    val rssi: Int,

    @field:Json(name = "txPower")
    val txPower: Int
)

