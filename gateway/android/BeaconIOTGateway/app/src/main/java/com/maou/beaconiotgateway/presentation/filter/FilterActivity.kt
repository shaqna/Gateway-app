package com.maou.beaconiotgateway.presentation.filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.maou.beaconiotgateway.databinding.ActivityFilterBinding
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop
import com.maou.beaconiotgateway.presentation.scan.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FilterActivity : AppCompatActivity() {

    private val binding: ActivityFilterBinding by lazy {
        ActivityFilterBinding.inflate(layoutInflater)
    }

    private val viewModel: FilterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(FilterViewModel.inject())

        fetchData()
        observeBus()
        observeBusStop()
        setButtonAction()

    }

    private fun setButtonAction() {
        binding.btnGoToScan.setOnClickListener {
            if (viewModel.busTarget.isNotEmpty() && viewModel.busStopTarget.isNotEmpty()) {
                Intent(this@FilterActivity, MainActivity::class.java).also {
                    it.putParcelableArrayListExtra(MainActivity.BUS, ArrayList(viewModel.busTarget))
                    it.putParcelableArrayListExtra(MainActivity.BUS_STOP, ArrayList(viewModel.busStopTarget))
                    startActivity(it)
                }
            }
        }
    }


    private fun fetchData() {
        viewModel.getBus()
        viewModel.getBusStop()
    }


    private fun observeBus() {
        viewModel.buses.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleBusState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeBusStop() {
        viewModel.busStops.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleBusStopState(state)
            }.launchIn(lifecycleScope)
    }

    private fun handleBusStopState(state: BusStopState) {
        when (state) {
            BusStopState.InitBusStop -> Unit
            is BusStopState.OnErrorFetching -> showToast(state.message)
            is BusStopState.OnSuccessFetching -> setBusStop(state.busStops)
        }
    }


    private fun handleBusState(state: BusState) {
        when (state) {
            BusState.InitBusState -> Unit
            is BusState.OnErrorFetching -> showToast(state.message)
            is BusState.OnSuccessFetching -> setBus(state.buses)
        }
    }

    private fun setBus(buses: List<Bus>) {
        Log.d("BUS", buses.toString())

        viewModel.setSelectedBus(buses)
    }

    private fun setBusStop(busStops: List<BusStop>) {
        viewModel.setSelectedBusStop(busStops)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@FilterActivity, message, Toast.LENGTH_SHORT).show()
    }


}