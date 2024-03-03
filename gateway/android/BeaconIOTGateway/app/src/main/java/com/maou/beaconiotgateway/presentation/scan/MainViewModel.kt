package com.maou.beaconiotgateway.presentation.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.domain.controller.BluetoothLeController
import com.maou.beaconiotgateway.domain.model.BleDevice
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop
import com.maou.beaconiotgateway.domain.usecase.BeaconUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class MainViewModel(
    private val bluetoothLeController: BluetoothLeController,
    private val beaconUseCase: BeaconUseCase
) : ViewModel() {

    var bus: List<Bus> = arrayListOf()
    var busStop: BusStop? = null

    private val _state = MutableStateFlow(BluetoothLeUiState())
    val state = combine(
        bluetoothLeController.scannedDevice,
        _state
    ) { scannedDevice, state ->
        state.copy(scannedDevices = scannedDevice)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    private val _beaconState = MutableStateFlow<BeaconUiState>(BeaconUiState.InitBeaconState)

    val beaconState get() = _beaconState

    private var _distanceScanning: Int = 0
    val distanceScanning get() = _distanceScanning

    fun setBusTarget(busTarget: List<Bus>) {
        bus = busTarget
    }

    fun setBusStopTarget(busStopTarget: BusStop) {
        busStop = busStopTarget
    }

    fun startLeScanning() {
        _distanceScanning += 1
        bus.let { bluetoothLeController.startLeScanning(it) }
    }

    fun stopLeScanning() {
        bluetoothLeController.stopLeScanning()
    }

    fun sendBeaconData(bleDevice: BleDevice, androidID: String) {
        viewModelScope.launch {
            beaconUseCase.sendBeaconData(bleDevice, androidID).collect { result ->
                when (result) {
                    is BaseResult.Error -> _beaconState.value =
                        BeaconUiState.OnErrorAction(result.message)

                    is BaseResult.Success -> _beaconState.value =
                        BeaconUiState.OnSuccessAction(result.data)

                    else -> {}
                }
            }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::MainViewModel)
        }
    }
}

data class BluetoothLeUiState(
    val scannedDevices: List<BleDevice> = emptyList()
)

sealed class BeaconUiState {
    object InitBeaconState : BeaconUiState()
    data class OnSuccessAction(val message: String) : BeaconUiState()
    data class OnErrorAction(val message: String) : BeaconUiState()
}