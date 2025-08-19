package com.phillqins.droidglossary.di

import com.phillqins.droidglossary.data.network.APIService
import com.phillqins.droidglossary.data.network.HttpClientProvider
import com.phillqins.droidglossary.data.repositories.AuthRepositoryImpl
import com.phillqins.droidglossary.data.repositories.GlossaryRepositoryImpl
import com.phillqins.droidglossary.domain.AuthRepository
import com.phillqins.droidglossary.domain.GlossaryRepository
import com.phillqins.droidglossary.ui.screens.auth.SignInViewModel
import com.phillqins.droidglossary.ui.screens.home.HomeViewModel
import com.phillqins.droidglossary.utils.AuthSessionManager
import com.phillqins.droidglossary.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { HttpClientProvider.create(context = androidContext()) }
    single { APIService(get()) }
    single { Dispatchers.IO }
    single <SessionManager>{ AuthSessionManager(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<GlossaryRepository> { GlossaryRepositoryImpl(get(), get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }

}