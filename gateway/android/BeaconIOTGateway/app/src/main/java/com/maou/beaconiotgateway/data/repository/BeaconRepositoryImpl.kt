package com.maou.beaconiotgateway.data.repository

import android.util.Log
import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.data.source.request.BeaconRequest
import com.maou.beaconiotgateway.data.source.request.DeviceIDItem
import com.maou.beaconiotgateway.data.source.request.DeviceIDRequest
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

    override suspend fun sendBeaconData(bleDevice: BleDevice, uniqueID: String, busStopName: String): Flow<BaseResult<String, String>> =
        flow {
            val item = Item(
                timestamp = bleDevice.timestamp,
                deviceID = uniqueID,
                distance = bleDevice.distance,
                txPower = bleDevice.txPower,
                rssi = bleDevice.rssi,
                proximityUUID = bleDevice.proximityUUID ?: "Unknown",
                bleAddress = bleDevice.deviceAddress,
                busStopName = busStopName
            )
            val response = apiService.sendBeaconData(BeaconRequest(payload = Payload(item = item)))
            Log.d("Repository", "response code ${uniqueID}")
            if (response.statusCode != 200) {
                emit(BaseResult.Error("Something wrong"))
            }

            emit(BaseResult.Success("Success"))

        }.catch { e ->
            emit(BaseResult.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)

    override fun sendDeviceID(deviceId: String): Flow<BaseResult<String, String>> {
        return flow {
            val deviceIDItem = DeviceIDItem(deviceId)
            val response = apiService.sendDeviceID(DeviceIDRequest(deviceIDItem))

            if(response.statusCode != 200) {
                emit(BaseResult.Error("Something wrong"))
            }

            emit(BaseResult.Success("Success"))
        }.catch { e ->
            emit(BaseResult.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
}