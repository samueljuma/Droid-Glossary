package com.phillqins.droidglossary.ui.navigation

sealed class AppScreens(val route: String){
    object SignIn: AppScreens("sign_in")
    object Home: AppScreens("home")
}
