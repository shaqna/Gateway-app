package com.maou.beaconiotgateway.domain.repository

import android.content.Context
import com.maou.beaconiotgateway.domain.model.Bus

interface BLERepository {
    fun startScan(scanTarget: List<Bus>, context: Context)
    fun stopScan(context: Context)
}