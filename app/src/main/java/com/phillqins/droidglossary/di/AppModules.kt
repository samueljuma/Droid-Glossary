package com.phillqins.droidglossary.di

import com.phillqins.droidglossary.data.network.APIService
import com.phillqins.droidglossary.data.network.HttpClientProvider
import com.phillqins.droidglossary.data.repositories.DroidGlossaryRepository
import com.phillqins.droidglossary.ui.screens.auth.SignInViewModel
import com.phillqins.droidglossary.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { HttpClientProvider.create() }
    single { APIService(get()) }
    single { Dispatchers.IO }
    single { DroidGlossaryRepository(get(), get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { HomeViewModel(get()) }

}