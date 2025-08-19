package com.phillqins.droidglossary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phillqins.droidglossary.ui.screens.auth.SignInScreen
import com.phillqins.droidglossary.ui.screens.auth.SignInViewModel
import com.phillqins.droidglossary.ui.screens.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val signInViewModel: SignInViewModel = koinViewModel()

    val userIsLoggedIn = signInViewModel.isUserLoggedIn()

    val startDestination = if (userIsLoggedIn) AppScreens.Home.route else AppScreens.SignIn.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = AppScreens.SignIn.route) {
            SignInScreen(navController = navController, viewModel = signInViewModel)
        }
        composable(route = AppScreens.Home.route){
            HomeScreen(navController = navController, viewModel = koinViewModel())
        }
    }
}