package com.maou.beaconiotgateway.data.controller

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log

class BleScanCallback(
    private val onDeviceFound: (ScanResult) -> Unit
): ScanCallback() {
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        Log.d("Beacon", "Scanning")
        result?.let {
            onDeviceFound(it)
        }
    }
}