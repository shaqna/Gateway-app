package com.maou.busapp.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.core.content.getSystemService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
import com.maou.busapp.R
import com.maou.busapp.databinding.ActivityMapsBinding
import com.maou.busapp.extensions.GpsEnableListener
import com.maou.busapp.presentation.model.CurrentLocation

class UiHelper(private val context: Context) {

    fun getConnectivityStatus(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun isPlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return ConnectionResult.SUCCESS == status
    }

    fun toast(content: String) = Toast.makeText(context, content, Toast.LENGTH_SHORT).show()

    fun showSnackBar(view: View, content: String) =
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show()

    fun getLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000)
        return locationRequest.build()
    }

    fun isLocationProviderEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun showPositiveDialogListener(
        activity: Activity,
        title: String,
        content: String,
        listener: GpsEnableListener,
        positiveText: String,
        cancelable: Boolean
    ) {

        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle).apply {
            setTitle(title)
            setMessage(content)
            setCancelable(cancelable)
            setPositiveButton(positiveText) { dialog, _ ->
                listener.onPositive()
                dialog.dismiss()
            }
        }

        val alert = builder.create()

        if(!alert.isShowing)
            alert.show()

    }

    fun setBottomSheetView(currentLocation: CurrentLocation) {

    }
}