package com.maou.beaconiotgateway.domain.usecase.device_id

import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.domain.repository.BeaconRepository
import kotlinx.coroutines.flow.Flow

class DeviceIDInteractor(private val beaconRepository: BeaconRepository): DeviceIDUseCase {
    override fun sendDeviceId(deviceID: String): Flow<BaseResult<String, String>> {
        return beaconRepository.sendDeviceID(deviceID)
    }
}