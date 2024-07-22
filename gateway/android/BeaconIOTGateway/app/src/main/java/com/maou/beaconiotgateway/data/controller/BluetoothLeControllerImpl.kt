package com.maou.beaconiotgateway.data.controller

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import com.maou.beaconiotgateway.domain.controller.BluetoothLeController
import com.maou.beaconiotgateway.domain.model.BleDevice
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.presentation.scan.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat
import kotlin.math.pow

@SuppressLint("MissingPermission")
class BluetoothLeControllerImpl(
    private val context: Context,
) : BluetoothLeController {

    companion object {
        const val BEACON_LEMON = "C1:43:C1:7D:B3:C4"
        const val BEACON_COCONUT = "C8:BF:9D:4C:8E:EB"
    }

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        bluetoothManager.adapter
    }

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()


    private val bleScanCallback = BleScanCallback { result ->
        val addressToFind = result.device.address
        val beaconWithAddress = _scanListTarget.value.find {
            it.address == addressToFind
        }
        val maxRssiTarget = result.rssi

        Log.d("ScannerBeacon", beaconWithAddress.toString())

        // if(beaconWithAddress != null  && maxRssiTarget <= -50)
        if (beaconWithAddress != null) {
            Log.d(
                "Beacon",
                """
                    txPower = ${result.txPower}
                    bleAddress = ${result.device.address}
                    uuid = ${result.scanRecord?.serviceUuids}
                    distance = ${calculateDistance(result.rssi, result.txPower)}
                 
                """.trimIndent()
            )

            _scannedDevice.update { bleDevices ->
                val currentTimestamp = System.currentTimeMillis()

                Log.d("BleController", currentTimestamp.toString())

                val proxUUID = if (result.scanRecord?.serviceUuids?.get(0) == null)
                    "Unknown"
                else
                    result.scanRecord?.serviceUuids?.get(0).toString()


                val newDevice = BleDevice(
                    deviceAddress = result.device.address,
                    rssi = result.rssi,
                    timestamp = currentTimestamp,
                    txPower = result.txPower,
                    proximityUUID = proxUUID,
                    distance = calculateDistance(result.rssi, result.txPower).toDouble()
                )
                Log.d(MainActivity.TAG, newDevice.toString())
                Log.d(MainActivity.TAG, result.scanRecord?.serviceUuids?.get(0).toString())
                bleDevices + newDevice
            }
            Log.d(MainActivity.TAG, _scannedDevice.value.size.toString())
        }
    }

    private val _scannedDevice = MutableStateFlow<List<BleDevice>>(emptyList())

    private val _scanListTarget = MutableStateFlow<List<Bus>>(emptyList())

    private fun calculateDistance(rssi: Int, txPower: Int): Double {
        val ratio = rssi * 1.0 / txPower
        val df = DecimalFormat("#.################")
        try {
            return if (ratio < 1.0) {
                // df.format(ratio.pow(10.0)).toDouble()
                ratio.pow(10.0)
            } else {
                val accuracy = 0.89976 * ratio.pow(7.7095) + 0.111
                //df.format(accuracy).toDouble()
                accuracy
            }
        } catch (e: NumberFormatException) {
            Log.e("Number Error", e.message.toString())
        }

        return 0.0
    }

    override val scannedDevice: StateFlow<List<BleDevice>>
        get() = _scannedDevice.asStateFlow()

    override fun startLeScanning(scanTarget: List<Bus>) {
        Log.d("Scanner", scanTarget.toString())

        _scanListTarget.update {
            scanTarget
        }
        _scannedDevice.update {
            emptyList()
        }
        bleScanner.startScan(null, scanSettings, bleScanCallback)

    }

    override fun stopLeScanning() {
        bleScanner.stopScan(bleScanCallback)

    }
}