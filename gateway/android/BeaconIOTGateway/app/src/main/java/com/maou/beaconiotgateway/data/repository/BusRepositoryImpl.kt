package com.maou.beaconiotgateway.data.repository

import com.maou.beaconiotgateway.data.BaseResult
import com.maou.beaconiotgateway.data.source.service.BusService
import com.maou.beaconiotgateway.domain.model.Bus
import com.maou.beaconiotgateway.domain.model.BusStop
import com.maou.beaconiotgateway.domain.repository.BusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BusRepositoryImpl(private val busService: BusService): BusRepository {
    override fun getBus(): Flow<BaseResult<List<Bus>, String>> =
        flow {
            val response = busService.getBus()
            val listBus = response.data
            if(response.code != 200) {
                emit(BaseResult.Error("gagal"))
            }
            emit(BaseResult.Success(listBus))
        }.catch {e ->
            emit(BaseResult.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)

    override fun getBusStop(): Flow<BaseResult<List<BusStop>, String>> =
        flow {
            val response = busService.getBusStop()
            val listBus = response.data
            if(response.code != 200) {
                emit(BaseResult.Error("gagal"))
            }
            emit(BaseResult.Success(listBus))
        }.catch {e ->
            emit(BaseResult.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)
}