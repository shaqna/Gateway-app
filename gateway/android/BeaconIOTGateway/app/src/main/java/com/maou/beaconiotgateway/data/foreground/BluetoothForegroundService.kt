package com.maou.beaconiotgateway.data.foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.maou.beaconiotgateway.R
import com.maou.beaconiotgateway.domain.controller.BluetoothLeController
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.presentation.scan.MainActivity
import com.maou.beaconiotgateway.utils.Constants.CHANNEL_ID
import com.maou.beaconiotgateway.utils.Constants.NOTIFICATION_ID
import org.koin.android.ext.android.inject

class BluetoothForegroundService : Service() {

    private val bluetoothLeController: BluetoothLeController by inject()
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()

        val pendingIntent =
            PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("BLE Scan Running")
            .setContentText("Scanning for BLE devices")
            .setSmallIcon(R.drawable.baseline_bluetooth_searching_24)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        intent?.let {
            when (it.action) {
                ACTION_START_SCAN -> {
                    val scanTargets =
                        it.getParcelableArrayListExtra<Bus>(EXTRA_SCAN_TARGETS)?.toList()
                    bluetoothLeController.startLeScanning(scanTargets!!)
                }

                ACTION_STOP_SCAN -> bluetoothLeController.stopLeScanning()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothLeController.stopLeScanning()
    }

    companion object {
        const val ACTION_START_SCAN = "ACTION_START_SCAN"
        const val ACTION_STOP_SCAN = "ACTION_STOP_SCAN"
        const val EXTRA_SCAN_TARGETS = "EXTRA_SCAN_TARGETS"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Location Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}