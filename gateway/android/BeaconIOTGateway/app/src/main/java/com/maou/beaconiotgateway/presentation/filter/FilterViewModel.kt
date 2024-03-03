package com.maou.beaconiotgateway.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop
import com.maou.beaconiotgateway.domain.usecase.bus.BusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class FilterViewModel(private val useCase: BusUseCase) : ViewModel() {

    var busStopTarget: BusStop? = null
    var busTarget: List<Bus> = arrayListOf()

    private val _buses: MutableStateFlow<BusState> = MutableStateFlow(BusState.InitBusState)
    val buses: StateFlow<BusState> get() = _buses

    private val _busStops: MutableStateFlow<BusStopState> =
        MutableStateFlow(BusStopState.InitBusStop)
    val busStops: StateFlow<BusStopState> get() = _busStops


    private fun showErrorBusMessage(message: String) {
        _buses.value = BusState.OnErrorFetching(message)
    }

    private fun showErrorBusStopMessage(message: String) {
        _busStops.value = BusStopState.OnErrorFetching(message)
    }

    fun setSelectedBusStop(busStop: BusStop) {
        busStopTarget = busStop
    }

    fun setSelectedBus(bus: List<Bus>) {
        busTarget = bus
    }

    fun getBus() {
        viewModelScope.launch {
            useCase.getBus()
                .collect { result ->

                    when (result) {
                        is BaseResult.Error -> showErrorBusMessage(result.message)
                        is BaseResult.Success -> _buses.value =
                            BusState.OnSuccessFetching(result.data)

                        else -> {}
                    }
                }

        }
    }

    fun getBusStop() {
        viewModelScope.launch {
            useCase.getBusStop()
                .collect { result ->
                    when (result) {
                        is BaseResult.Error -> showErrorBusStopMessage(result.message)
                        is BaseResult.Success -> _busStops.value =
                            BusStopState.OnSuccessFetching(result.data)

                        else -> {}
                    }
                }

        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::FilterViewModel)
        }
    }


}

sealed class BusState {
    object InitBusState : BusState()
    data class OnSuccessFetching(val buses: List<Bus>) : BusState()
    data class OnErrorFetching(val message: String) : BusState()
}

sealed class BusStopState {
    object InitBusStop : BusStopState()
    data class OnSuccessFetching(val busStops: List<BusStop>) : BusStopState()
    data class OnErrorFetching(val message: String) : BusStopState()
}