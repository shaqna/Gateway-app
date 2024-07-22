package com.maou.beaconiotgateway.utils

import com.maou.beaconiotgateway.utils.TimeHelper.timestampToDate
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date


class TimeHelperTest {

    @Test
    fun printTimestamp() {
        val currentTimestamp = System.currentTimeMillis()
        val date = Date(currentTimestamp)
        val formattedDate = SimpleDateFormat("HH:mm:ss").format(date)
        println(formattedDate)
    }
}