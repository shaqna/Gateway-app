package com.maou.beaconiotgateway.utils

object Constants {

    // const val BASE_URL = "https://xv9w9odxhg.execute-api.us-east-2.amazonaws.com/"

    const val BASE_URL = "https://cjue3pzetf.execute-api.ap-southeast-1.amazonaws.com/"
    const val USK_URL = "https://czbnab95tc.execute-api.ap-southeast-3.amazonaws.com/"


    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

}