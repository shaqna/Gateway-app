package com.maou.beaconiotgateway.di

import com.maou.beaconiotgateway.data.controller.BluetoothLeControllerImpl
import com.maou.beaconiotgateway.data.repository.BLERepositoryImpl
import com.maou.beaconiotgateway.data.repository.BeaconRepositoryImpl
import com.maou.beaconiotgateway.data.repository.BusRepositoryImpl
import com.maou.beaconiotgateway.domain.controller.BluetoothLeController
import com.maou.beaconiotgateway.domain.repository.BLERepository
import com.maou.beaconiotgateway.domain.repository.BeaconRepository
import com.maou.beaconiotgateway.domain.repository.BusRepository
import com.maou.beaconiotgateway.presentation.scan.MainActivity
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::BluetoothLeControllerImpl){
        bind<BluetoothLeController>()
    }

    singleOf(::BeaconRepositoryImpl) {
        bind<BeaconRepository>()
    }

    singleOf(::BusRepositoryImpl) {
        bind<BusRepository>()
    }

    singleOf(::BLERepositoryImpl) {
        bind<BLERepository>()
    }
}

