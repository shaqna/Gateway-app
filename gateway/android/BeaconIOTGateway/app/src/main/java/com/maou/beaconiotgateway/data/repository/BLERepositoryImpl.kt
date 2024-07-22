package com.maou.beaconiotgateway.data.repository

import android.content.Context
import android.content.Intent
import com.maou.beaconiotgateway.data.foreground.BluetoothForegroundService
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.repository.BLERepository
import java.util.ArrayList

class BLERepositoryImpl : BLERepository {
    override fun startScan(scanTarget: List<Bus>, context: Context) {
        Intent(context, BluetoothForegroundService::class.java).also {
            it.putParcelableArrayListExtra(
                BluetoothForegroundService.EXTRA_SCAN_TARGETS,
                ArrayList(scanTarget)
            )
            it.action = BluetoothForegroundService.ACTION_START_SCAN
            context.startService(it)
        }
    }

    override fun stopScan(context: Context) {
        Intent(context, BluetoothForegroundService::class.java).also {
            it.action = BluetoothForegroundService.ACTION_STOP_SCAN
            context.startService(it)
        }
    }
}