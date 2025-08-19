package com.phillqins.droidglossary.di

import com.phillqins.droidglossary.ui.screens.auth.SignInViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { SignInViewModel() }
}