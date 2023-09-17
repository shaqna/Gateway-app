package com.maou.beaconiotgateway.data.repository

import android.util.Log
import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.data.source.request.BeaconRequest
import com.maou.beaconiotgateway.data.source.request.Item
import com.maou.beaconiotgateway.data.source.request.Payload
import com.maou.beaconiotgateway.data.source.service.ApiService
import com.maou.beaconiotgateway.domain.model.BleDevice
import com.maou.beaconiotgateway.domain.repository.BeaconRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BeaconRepositoryImpl(
    private val apiService: ApiService
) : BeaconRepository {

    override suspend fun sendBeaconData(bleDevice: BleDevice, uniqueID: String): Flow<BaseResult<String, String>> =
        flow {
            val payload = Payload(
                timestamp = bleDevice.timestamp,
                deviceID = uniqueID,
                distance = bleDevice.distance,
                txPower = bleDevice.txPower,
                rssi = bleDevice.rssi,
                proximityUUID = bleDevice.proximityUUID,
                bleAddress = bleDevice.deviceAddress
            )
            val response = apiService.sendBeaconData(BeaconRequest(payload = Item(item = payload)))
            Log.d("Repository", "response code ${response.statusCode}")
            if (response.statusCode != 200) {
                emit(BaseResult.Error("Something wrong"))
            }

            emit(BaseResult.Success("Success"))

        }.catch { e ->
            emit(BaseResult.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)
}