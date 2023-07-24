package com.maou.beaconiotgateway.domain.usecase.bus

import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop
import com.maou.beaconiotgateway.domain.repository.BusRepository
import kotlinx.coroutines.flow.Flow

class BusInteractor(private val busRepository: BusRepository): BusUseCase {
    override fun getBus(): Flow<BaseResult<List<Bus>, String>> {
        return busRepository.getBus()
    }

    override fun getBusStop(): Flow<BaseResult<List<BusStop>, String>> {
        return busRepository.getBusStop()
    }
}