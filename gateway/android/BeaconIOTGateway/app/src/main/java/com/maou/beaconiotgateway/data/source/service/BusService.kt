package com.maou.beaconiotgateway.data.source.service

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.maou.beaconiotgateway.data.source.response.BusResponse
import com.maou.beaconiotgateway.data.source.response.BusStopResponse
import java.io.IOException

class BusService(private val context: Context)  {

    fun getBus(): BusResponse {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("bus.json")
                .bufferedReader()
                .use {
                    it.readText()
                }

        } catch (ioException: IOException) {
            Log.d("Service Error", ioException.toString())
        }

        return Gson().fromJson(jsonString, BusResponse::class.java)
    }

    fun getBusStop(): BusStopResponse {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("bus_stop.json")
                .bufferedReader()
                .use {
                    it.readText()
                }
        } catch (ioException: IOException) {
            Log.d("Service Error", ioException.toString())
        }

        return Gson().fromJson(jsonString, BusStopResponse::class.java)
    }

}