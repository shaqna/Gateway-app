package com.maou.beaconiotgateway.di

import com.maou.beaconiotgateway.domain.usecase.beacon.BeaconInteractor
import com.maou.beaconiotgateway.domain.usecase.beacon.BeaconUseCase
import com.maou.beaconiotgateway.domain.usecase.bus.BusInteractor
import com.maou.beaconiotgateway.domain.usecase.bus.BusUseCase
import com.maou.beaconiotgateway.domain.usecase.device_id.DeviceIDInteractor
import com.maou.beaconiotgateway.domain.usecase.device_id.DeviceIDUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::BeaconInteractor) {
        bind<BeaconUseCase>()
    }

    singleOf(::BusInteractor) {
        bind<BusUseCase>()
    }

    singleOf(::DeviceIDInteractor) {
        bind<DeviceIDUseCase>()
    }
}