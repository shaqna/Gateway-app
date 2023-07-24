package com.maou.beaconiotgateway.domain.usecase.bus

import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop
import kotlinx.coroutines.flow.Flow

interface BusUseCase {
    fun getBus(): Flow<BaseResult<List<Bus>, String>>
    fun getBusStop(): Flow<BaseResult<List<BusStop>, String>>
}