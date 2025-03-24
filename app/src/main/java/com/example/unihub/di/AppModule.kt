package com.example.unihub.di

import com.example.unihub.core.data.networking.HttpClientFactory
import com.example.unihub.uniclub.data.network.RemoteUniclubDataSource
import com.example.unihub.uniclub.data.network.TokenExpirationHandler
import com.example.unihub.uniclub.data.repository.DefaultUniclubRepository
import com.example.unihub.uniclub.domain.UniclubDataSource
import com.example.unihub.uniclub.domain.UniclubRepository
import com.example.unihub.uniclub.presentation.authen.login.LoginViewModel
import com.example.unihub.uniclub.presentation.home.HomeViewModel
import com.example.unihub.uniclub.presentation.main.MainViewModel
import com.example.unihub.uniclub.util.AppPreferencesDataSource
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }

    single { AppPreferencesDataSource(androidContext()) }

    singleOf(::RemoteUniclubDataSource).bind<UniclubDataSource>()

    singleOf(::DefaultUniclubRepository).bind<UniclubRepository>()

    single<UniclubDataSource> { RemoteUniclubDataSource(get(), get()) }


    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
}


val authModule = module {
    single { TokenExpirationHandler(get()) }
}