package com.maou.beaconiotgateway.domain.controller

import com.maou.beaconiotgateway.domain.model.BleDevice
import com.maou.beaconiotgateway.domain.model.Bus
import kotlinx.coroutines.flow.StateFlow

interface BluetoothLeController {

    val scannedDevice: StateFlow<List<BleDevice>>

    fun startLeScanning(scanTarget: List<Bus>)
    fun stopLeScanning()
}