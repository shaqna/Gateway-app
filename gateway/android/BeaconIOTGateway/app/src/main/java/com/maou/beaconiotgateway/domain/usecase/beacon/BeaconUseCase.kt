package com.maou.beaconiotgateway.domain.usecase.beacon

import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.domain.model.BleDevice
import kotlinx.coroutines.flow.Flow

interface BeaconUseCase {

    suspend fun sendBeaconData(
        bleDevice: BleDevice,
        uniqueID: String,
        busStopName: String
    ): Flow<BaseResult<String, String>>

}