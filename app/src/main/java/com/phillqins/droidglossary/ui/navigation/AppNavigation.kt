package com.phillqins.droidglossary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phillqins.droidglossary.ui.screens.auth.SignInScreen
import com.phillqins.droidglossary.ui.screens.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SignIn.route
    ){
        composable(route = AppScreens.SignIn.route) {
            SignInScreen(navController = navController, viewModel = koinViewModel())
        }
        composable(route = AppScreens.Home.route){
            HomeScreen(navController = navController, viewModel = koinViewModel())
        }
    }
}