package com.maou.beaconiotgateway.domain.usecase.device_id

import com.maou.beaconiotgateway.data.BaseResult
import kotlinx.coroutines.flow.Flow

interface DeviceIDUseCase {
    fun sendDeviceId(deviceID: String): Flow<BaseResult<String, String>>
}