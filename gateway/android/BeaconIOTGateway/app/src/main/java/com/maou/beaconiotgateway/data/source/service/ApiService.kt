package com.maou.beaconiotgateway.data.source.service

import com.maou.beaconiotgateway.data.source.request.BeaconRequest
import com.maou.beaconiotgateway.data.source.request.DeviceIDRequest
import com.maou.beaconiotgateway.data.source.response.GeneralResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
//
//    @POST("default/gateway/postbeacon")
//    suspend fun sendBeaconData(
//        @Body beaconRequest: BeaconRequest
//    ): GeneralResponse

    @POST("deploynew/bus")
    suspend fun sendBeaconData(
        @Body beaconRequest: BeaconRequest
    ): GeneralResponse

    @POST("deploynew/update-gateway-deviceID")
    suspend fun sendDeviceID(
        @Body deviceIdRequest: DeviceIDRequest
    ): GeneralResponse
}